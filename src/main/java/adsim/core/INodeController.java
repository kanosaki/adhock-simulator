package adsim.core;


public interface INodeController {
	void handle(INode node);
	
	void onPacketReceived(IPacket packet);

	INodeController clone();
}
