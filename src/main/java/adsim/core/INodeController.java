package adsim.core;


public interface INodeController {
	void interval(INode node);
	
	void onCross(INode self, IPacket packet);

	INodeController clone();
}
