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
	private ArrayList<INodeController> controllers;
	private final Queue<INodeController> addingController;
	private Queue<IPacket> sendQueue;

	public Node() {
		this.controllers = new ArrayList<INodeController>();
		this.sendQueue = new ConcurrentLinkedQueue<IPacket>();
		this.addingController = new ConcurrentLinkedQueue<INodeController>();
	}

	@Override
	public Vector getPosition() {
		return Vector.zero;
	}

	@Override
	public double getRadioPower() {
		return 10;
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


}
