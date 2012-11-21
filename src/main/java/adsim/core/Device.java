package adsim.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.*;
import adsim.misc.Vector;

public class Device {
    public static final double DEFAULT_INITIAL_RADIOOOWER = 10.0;
    @Getter
    @Setter
    private Vector position;

    @Getter
    @Setter
    private double radioPower;
    
    @Setter @Getter
    private double bound;

    private Queue<Message> sendQueue;

    private Queue<Message> recvQueue;

    public Device() {
        this.position = Vector.zero;
        this.radioPower = DEFAULT_INITIAL_RADIOOOWER;
        this.sendQueue = new ConcurrentLinkedQueue<Message>();
        this.recvQueue = new ConcurrentLinkedQueue<Message>();
    }

    public Message offerSend() {
        return this.sendQueue.poll();
    }

    public void pushPacket(Message packet) {
        this.recvQueue.add(packet);
    }

    public void send(Message packet) {
        this.sendQueue.add(packet);
    }

    public Message recv() {
        return this.recvQueue.poll();
    }

    public Device clone() {
        val newone = new Device();
        newone.position = this.position;
        newone.radioPower = this.radioPower;
        newone.sendQueue = new ConcurrentLinkedQueue<Message>(sendQueue);
        newone.recvQueue = new ConcurrentLinkedQueue<Message>(recvQueue);
        return newone;
    }

    public int tellOverflowed() {
        int overflowPackets = sendQueue.size();
        sendQueue.clear();
        return overflowPackets;
    }
}
