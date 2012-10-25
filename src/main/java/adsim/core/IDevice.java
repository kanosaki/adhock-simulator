package adsim.core;

import java.util.Queue;

/**
 * Physical model for Node and adapter between simulator facilities and user
 * defined node behavior.
 */
public interface IDevice {
	/**
	 * Offer device to send an packet. Returned IPacket object will be broadcast
	 * to all neighbor nodes.
	 * 
	 * @return IPacket object or null(If there aren't any IPacket objects which
	 *         want to send.).
	 */
	IPacket offerSend();

	/**
	 * All packet sent from near node will be delivered by calling this method
	 * despite it is not for this node(device).
	 * 
	 * @param packet
	 *            Packet body
	 */
	void receive(IPacket packet);

}
