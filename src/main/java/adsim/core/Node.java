package adsim.core;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import adsim.Util;
import adsim.core.Message.TellNeighbors;
import adsim.handler.SignalArgs;
import adsim.handler.VoidHandler;
import adsim.misc.Vector;

@Slf4j
public class Node implements Comparable<Node> {
    public static final int INITIAL_BUFFER_MAX = 10;

    // -- Signals --
    public static final String SIGNAL_COLLECT_BUFFER = "Node/RequestCollectBuffer";

    // -------------

    @Getter
    private final NodeID id;
    @Getter
    private INodeHandler handler;
    private final ArrayList<Message.Envelope> msgBuffer;
    @Getter
    private ArrayList<Message.Envelope> createdMessages;

    @Getter
    private Map<NodeID, Integer> weightMap;

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

    @Getter
    @Setter
    private Vector currentDestination;

    private HashSet<Long> receivedMessages;

    @Getter
    @Setter
    private boolean verbose;

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
        this.msgBuffer = new ArrayList<Message.Envelope>();
        this.friends = new ArrayList<Node>();
        this.createdMessages = new ArrayList<Message.Envelope>();
        this.roundPoints = new ArrayList<Vector>();
        this.receivedMessages = new HashSet<Long>();
        this.weightMap = new TreeMap<NodeID, Integer>();
        this.handler = handler;
    }

    public void onSessionInitialized() {
        initLocation();
        handler.initialize(this);
        log.info(String.format("%s ready. Friends:%d, RoundPoints:%d", this,
                friends.size(),
                roundPoints.size()));
    }

    private void initLocation() {
        val initPoint = Util.randomSelect(getRoundPoints());
        jump(initPoint);
        if (getRoundPoints().size() >= 2) {
            setCurrentDestination(Util.randomSelectExcept(
                    getRoundPoints(), initPoint));
        } else {
            setCurrentDestination(initPoint);
        }
    }

    // ------------------------------
    // --- interface for handlers ---

    public List<Message.Envelope> getBuffer() {
        return msgBuffer;
    }

    public boolean isBufferFilled() {
        return msgBuffer.size() >= getBufferMax();
    }

    public void broadcast(Message msg) {
        debug("Broadcasting %s", msg);
        device.send(msg);
    }

    public void pushMessage(Message.Envelope packet) {
        if (isBufferFilled()) {
            throw new IllegalStateException("Buffer is overloaded.");
        }
        debug("PushMessage %s", packet);
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
        debug("MessageCreated %s", newmsg);
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
        debug("DisposingMessage at %d (%s)", index, msgBuffer.get(index));
        msgBuffer.remove(index);
    }

    /**
     * 現在の座標から指定されたベクトル分移動します。相対位置移動です
     * 
     * @param v
     *            移動するベクトル
     */
    public void move(Vector v) {
        debug("Move %s", v);
        val point = device.getPosition().add(v);
        device.setPosition(point);
    }

    /**
     * 指定された座標へ移動します。絶対位置移動です
     * 
     * @param v
     *            移動先の位置
     */
    public void jump(Vector v) {
        debug("Jump %s", v);
        device.setPosition(v);
    }

    public Vector getLocation() {
        return device.getPosition();
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
        debug("Signal %s from %s(arg: %s)", name, sender, arg);
        handler.onSignal(name, sender, arg);
    }

    public void fireSignal(String name, INodeHandler sender) {
        fireSignal(name, sender, SignalArgs.Void);
    }

    public void sortBuffer(Comparator<Message.Envelope> sortComparator) {
        Collections.sort(msgBuffer, sortComparator);
    }

    // --- interface for handlers END ---
    // ----------------------------------

    /**
     * deviceから受信したメッセージを取得し処理します
     */
    private void retrieveMessages() {
        Message msg = null;
        while ((msg = device.recv()) != null) {
            // receivedMessagesにすでに存在する場合は、重なって受信してるので無視します
            if (receivedMessages.contains(msg.getId())) {
                continue;
            }
            receivedMessages.add(msg.getId());
            switch (msg.getType()) {
            case Message.TYPE_ENVELOPE:
                handleEnvelope((Message.Envelope) msg);
                break;
            case Message.TYPE_TELLNEIGHBORS:
                handleTellNeighbors((Message.TellNeighbors) msg);
                break;
            default:
                log.warn("Unknown Message type : " + msg);
                break;
            }
            handler.onReceived(this, msg);
        }
    }

    private void handleEnvelope(Message.Envelope envelope) {
        debug("RecvEnvelope", envelope);
        if (envelope.getToId().equals(id)) {
            debug("AcceptEnvelope %s", envelope);
            session.onMessageAccepted(this, envelope);
        }
        msgBuffer.add(envelope);
    }

    private void handleTellNeighbors(Message.TellNeighbors packet) {
        debug("RecvTellNeighbors %s", packet);
        updateWeightMap(packet);
    }

    private void updateWeightMap(TellNeighbors packet) {
        // ここでTellNeighborsパケットの距離値を減算します
        weightMap.put(packet.getSender(), packet.getDistance() - 1);
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

    @Override
    public int compareTo(Node arg0) {
        return this.getId().compareTo(arg0.getId());
    }

    public void debug(String format, Object... args) {
        if (verbose) {
            val fmt = String.format("%s: %s", this, format);
            log.debug(String.format(fmt, args));
        }
    }
}
