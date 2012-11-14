package adsim.core;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import adsim.handler.VoidHandler;
import adsim.misc.Vector;

@Slf4j
public class Node {
    public static final int INITIAL_BUFFER_MAX = 10;

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

    private Session session;

    // ----- Out of model ----
    @Getter
    private ArrayList<Node> friends;

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

    public Node(NodeID id, INodeHandler handler) {
        this.id = id;
        this.setBufferMax(INITIAL_BUFFER_MAX);
        this.device = new Device();
        this.msgBuffer = new ArrayList<Message>();
        this.friends = new ArrayList<Node>();
        this.createdMessages = new ArrayList<Message>();
        this.handler = handler;
    }

    // ------------------------------
    // --- interface for handlers ---

    public List<Message> getBuffer() {
        return msgBuffer;
    }

    public boolean isBufferFilled() {
        return msgBuffer.size() >= getBufferMax();
    }

    public void injectDevice(Device device) {
        this.device = (Device) device;
    }

    public void injectSession(Session session) {
        this.session = session;
    }

    public void broadcast(Message msg) {
        device.send(msg);
        log.debug(String.format("MESSAGE SENT: %s -> %s", id,
                msg.getDestinationId()));
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
        val newmsg = new Message(getId(), node.getId());
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

    public void fireSignal(String name, INodeHandler sender, Object arg) {
        handler.onSignal(name, sender, arg);
    }

    // --- interface for handlers END ---
    // ----------------------------------

    /**
     * deviceから受信したメッセージを取得し処理します
     */
    private void retrieveMessages() {
        Message msg = null;
        while ((msg = device.recv()) != null) {
            // 自分宛
            if (msg.getDestinationId().equals(id)) {
                acceptMessage(msg);
            } else { // 自分宛ではない
                // pushMessage経由では無く、容量を無視して追加します。
                // handlerの中に不要メッセージを捨てるNodeHandlerがある必要があります
                msgBuffer.add(msg);
            }
            handler.onReceived(this, msg);
        }
    }

    private void acceptMessage(Message msg) {
        log.debug(String.format("MESSAGE ACCEPTED: %s <- %s", id,
                msg.getSourceId()));
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

    @Override
    public String toString() {
        return String.format("<Node@%s>", id);
    }

    public enum Signals {
        ReqestCollectBuffer
    }
}
