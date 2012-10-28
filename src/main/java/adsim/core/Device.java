package adsim.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.*;
import adsim.misc.Vector;

public class Device implements IDevice {

	@Getter
	@Setter
	private Vector position;

	@Getter
	@Setter
	private double radioPower;

	private Queue<IPacket> sendQueue;

	private Queue<IPacket> recvQueue;

	public Device() {
		this.sendQueue = new ConcurrentLinkedQueue<IPacket>();
		this.recvQueue = new ConcurrentLinkedQueue<IPacket>();
	}
	
	public IPacket offerSend() {
		return this.sendQueue.peek();
	}

	public void pushPacket(IPacket packet) {
		this.recvQueue.add(packet);
	}

	@Override
	public void send(IPacket packet) {
		this.sendQueue.add(packet);
	}

	@Override
	public IPacket recv() {
		return this.recvQueue.poll();
	}
	
	public Device clone(){
		val newone = new Device();
		newone.position = this.position;
		newone.radioPower = this.radioPower;
		newone.sendQueue = new ConcurrentLinkedQueue<IPacket>(sendQueue);
		newone.recvQueue = new ConcurrentLinkedQueue<IPacket>(recvQueue);
		return newone;
	}
}
