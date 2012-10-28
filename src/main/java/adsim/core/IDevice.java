package adsim.core;

public interface IDevice {
	void send(IPacket packet);
	IPacket recv();
	
}
