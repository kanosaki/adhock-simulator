package adsim.core;

import java.util.Queue;

import adsim.misc.Vector;

/**
 * Logical model for Node
 */
public interface INode {
	void pushPacket(IPacket packet);

	Queue<IPacket> getSendQueue();

	void next();

	void addController(INodeController nc);

	INode clone();

	void injectDevice(IDevice device);

	IDevice getDevice();

}
