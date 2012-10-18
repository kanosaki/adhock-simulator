package adsim;

public interface INode {
	Vector getPosition();
	double getRadioPower();
	void receive(IPacket packet);
}
