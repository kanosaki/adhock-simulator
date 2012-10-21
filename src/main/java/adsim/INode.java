package adsim;

import java.util.Queue;

import adsim.core.INodeController;

public interface INode {
	Vector getPosition();
	double getRadioPower();
	void pushPacket(IPacket packet);
	Queue<IPacket> getSendQueue();
	void next();
	void addController(INodeController controller);
}
