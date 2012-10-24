package adsim;

import java.util.Queue;

import adsim.core.INodeController;
import adsim.misc.Vector;

public interface INode {
	Vector getPosition();

	double getRadioPower();

	void pushPacket(IPacket packet);

	Queue<IPacket> getSendQueue();

	void next();
}
