package adsim.core;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import adsim.handler.VoidHandler;
import adsim.misc.Vector;

public class Node {
    public static final int INITIAL_BUFFER_MAX = 10;

    @Getter
    private final String id;
    @Getter
    private INodeHandler handler;
    private final ArrayList<Message> msgBuffer;
    @Getter
    private int bufferMax;

    @Getter(value = AccessLevel.PROTECTED)
    private Device device;

    private Session session;

    // ----- Out of model ----
    private ArrayList<Message> createdMessages;

    // -----------------------

    protected static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    public Node() {
        this(VoidHandler.get());
    }

    public Node(INodeHandler handler) {
        this(generateRandomId(), handler);
    }

    public Node(String id, INodeHandler handler) {
        this(id, INITIAL_BUFFER_MAX, new Device(),
                new ArrayList<Message>(), handler);
    }

    // Copy constructor
    private Node(String id, int bufferMax, Device device,
            ArrayList<Message> buffer, INodeHandler handler) {
        this.id = id;
        this.setBufferMax(bufferMax);
        this.device = device;
        this.msgBuffer = buffer;
        this.handler = handler;
    }

    public void pushPacket(Message packet) {
        if (isBufferFilled()) {
            throw new IllegalStateException("Buffer is overloaded.");
        }
        msgBuffer.add(packet);
    }

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
    }

    /**
     * 監視付きメッセージを作成します。ここで作成されたメッセージは、エンジンによって行き先が追跡され、集計結果に反映されます
     * 
     * @return 作成されたメッセージ
     */
    public Message createMessage() {
        val newmsg = new Message();
        if (session != null) {
            session.onMessageCreated(this, newmsg);
        }
        return newmsg;
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

    public void next(Session sess) {
        Message msg = null;
        while ((msg = device.recv()) != null) {
            handler.onReceived(this, msg);
        }
        handler.interval(sess, this);
    }

    public Node clone() {
        val dev = device.clone();
        val newMsgBuffer = new ArrayList<Message>();
        for (val msg : msgBuffer) {
            newMsgBuffer.add(msg.clone());
        }
        return new Node(id, this.getBufferMax(), dev, newMsgBuffer,
                handler.clone());
    }

    private void setBufferMax(int bufferMax) {
        this.bufferMax = bufferMax;
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
}
