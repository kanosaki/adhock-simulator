package adsim.defaults;

import lombok.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import adsim.INode;
import adsim.IPacket;
import adsim.core.INodeController;
import adsim.misc.Vector;

public class Node implements INode {
    private static final double INITIAL_RADIO_POWER = 1.0;
    private ArrayList<INodeController> controllers;
    private Queue<IPacket> sendQueue;

    @Getter
    @Setter
    private double radioPower;

    @Getter
    @Setter
    private Vector position;

    public Node() {
        this(INITIAL_RADIO_POWER, Vector.zero, new ArrayList<INodeController>());
    }

    /**
     * Copy constructor
     */
    private Node(double radio, Vector pos, ArrayList<INodeController> controllers) {
        this.radioPower = radio;
        this.position = pos;
        this.controllers = controllers;
        this.sendQueue = new ConcurrentLinkedQueue<IPacket>();
    }

    @Override
    public void pushPacket(IPacket packet) {
        // TODO Auto-generated method stub

    }

    @Override
    public Queue<IPacket> getSendQueue() {
        return new LinkedList<IPacket>(this.sendQueue);
    }

    @Override
    public void next() {
        for (val controller : this.getControllers()) {
            controller.handle(this);
        }
    }

    private ArrayList<INodeController> getControllers() {
        return this.controllers;
    }

    @Override
    public void addController(INodeController nc) {
        this.controllers.add(nc);
    }

    @Override
    public INode clone() {
        val controllers = new ArrayList<INodeController>(this.controllers.size());
        for(val ctr : this.controllers) {
            controllers.add(ctr.clone());
        }
        return new Node(this.getRadioPower(), this.getPosition(), controllers);
    }
}
