package adsim.core;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import adsim.handler.SignalArgs;
import adsim.handler.VoidHandler;
import adsim.misc.Vector;

@Slf4j
public class Node {
    public static final int INITIAL_BUFFER_MAX = 10;

    // -- Signals --
    public static final String SIGNAL_COLLECT_BUFFER = "Node/RequestCollectBuffer";

    // -------------

    @Getter
    private final NodeID id;
    @Getter
    private INodeHandler handler;
    private final ArrayList<Message> msgBuffer;
    @Getter
    private ArrayList<Message> createdMessages;

    @Getter
    private int bufferMax;

    @Getter(value = AccessLevel.PROTECTED)
    private Device device;

    @Getter
    @Setter
    private Session session;

    // ----- Out of model ----
    @Getter
    private ArrayList<Node> friends;

    @Getter
    private ArrayList<Vector> roundPoints;
    
    private HashSet<Long> receivedMessages;

    // -----------------------

    protected static NodeID generateRandomId() {
        return new NodeID();
    }

    public Node() {
        this(VoidHandler.get());
    }

    public Node(INodeHandler handler) {
        this(generateRandomId(), handler);
    }

    public Node(INodeHandler... hdrs) {
        this(generateRandomId(), new CompositeNodeHandler(Arrays.asList(hdrs))
                .prune());
    }

    public Node(NodeID id, INodeHandler handler) {
        this.id = id;
        this.setBufferMax(INITIAL_BUFFER_MAX);
        this.device = new Device();
        this.msgBuffer = new ArrayList<Message>();
        this.friends = new ArrayList<Node>();
        this.createdMessages = new ArrayList<Message>();
        this.roundPoints = new ArrayList<Vector>();
        this.receivedMessages = new HashSet<Long>();
        this.handler = handler;
        init();
    }

    private void init() {
        handler.initialize(this);
    }

    // ------------------------------
    // --- interface for handlers ---

    public List<Message> getBuffer() {
        return msgBuffer;
    }

    public boolean isBufferFilled() {
        return msgBuffer.size() >= getBufferMax();
    }

    public void broadcast(Message msg) {
        device.send(msg);
        log.debug(String.format("MESSAGE SENT: <%s>[%s]", this, msg));
    }

    public void pushMessage(Message packet) {
        if (isBufferFilled()) {
            throw new IllegalStateException("Buffer is overloaded.");
        }
        msgBuffer.add(packet);
    }

    /**
     * 監視付きメッセージを作成します。ここで作成されたメッセージは、エンジンによって行き先が追跡され、集計結果に反映されます
     * 
     * @return 作成されたメッセージ
     */
    public void createMessage(Node node) {
        val newmsg = new Message.Envelope(getId(), node.getId());
        if (session != null) {
            session.onMessageCreated(this, newmsg);
        }
        createdMessages.add(newmsg);
    }

    /**
     * バッファからメッセージを除去します。
     * 
     * @param msg
     */
    public void disposeMessage(Message msg) {
        msgBuffer.remove(msg);
    }

    public void disposeMessage(int index) {
        msgBuffer.remove(index);
    }

    /**
     * 現在の座標から指定されたベクトル分移動します。相対位置移動です
     * 
     * @param v
     *            移動するベクトル
     */
    public void move(Vector v) {
        device.setPosition(device.getPosition().add(v));
    }

    /**
     * 指定された座標へ移動します。絶対位置移動です
     * 
     * @param v
     *            移動先の位置
     */
    public void moveTo(Vector v) {
        device.setPosition(v);
    }

    /**
     * シグナルを送信します
     * 
     * @param name
     *            シグナルの名前
     * @param sender
     *            シグナルを送信したINodeHandler
     * @param arg
     *            シグナルの引数
     */
    public void fireSignal(String name, INodeHandler sender, SignalArgs arg) {
        handler.onSignal(name, sender, arg);
    }

    public void fireSignal(String name, INodeHandler sender) {
        fireSignal(name, sender, SignalArgs.Void);
    }

    // --- interface for handlers END ---
    // ----------------------------------

    /**
     * deviceから受信したメッセージを取得し処理します
     */
    private void retrieveMessages() {
        Message msg = null;
        while ((msg = device.recv()) != null) {
            if (msg instanceof Message.Envelope) {
                acceptEnvelope((Message.Envelope) msg);
            } else { // 自分宛ではない
                // pushMessage経由では無く、容量を無視して追加します。
                // handlerの中に不要メッセージを捨てるNodeHandlerがある必要があります
                msgBuffer.add(msg);
            }
            handler.onReceived(this, msg);
        }
    }

    private void acceptEnvelope(Message.Envelope msg) {
        if (msg.getToId().equals(id)) {
            if(!receivedMessages.contains(msg.getId())) {
                receivedMessages.add(msg.getId());
                session.onMessageReached(this, msg);
            }
            log.debug(String.format("MESSAGE ACCEPTED: [%s]", msg));
        }
    }

    public void next(Session sess) {
        retrieveMessages();
        handler.interval(sess, this);
    }

    private void setBufferMax(int bufferMax) {
        this.bufferMax = bufferMax;
    }

    public void addFriend(Node newfriend) {
        if (!(friends.contains(newfriend) || this.equals(newfriend)))
            friends.add(newfriend);
    }

    public void addRoundPoint(Vector point) {
        roundPoints.add(point);
    }

    @Override
    public String toString() {
        return String.format("<Node@%s>", id.shortToString());
    }
}
