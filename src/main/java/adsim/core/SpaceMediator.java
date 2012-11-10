package adsim.core;

import java.util.List;

import lombok.*;

/**
 * 同一の空間、同一のチャンネルだと無線通信は混線するというルールをシミュレートします
 */
public class SpaceMediator {
	private final Field field;

	public SpaceMediator(Field field) {
		this.field = field;
	}

	public void addNode(Node node) {

	}

	private List<Device> getDevices() {
		return this.field.getDevices();
	}

	public void dispatch(Device srcnode, Message packet) {
		for (val dst_candidate : this.getDevices()) {
			if (dst_candidate.equals(srcnode))
				continue;
			val radius = srcnode.getRadioPower();
			val tgt_pos = dst_candidate.getPosition();
			val src_pos = srcnode.getPosition();
			val distanceSq = tgt_pos.disatnceSquare(src_pos);
			if (distanceSq <= radius) {
				dst_candidate.pushPacket(packet);
			}
		}
	}
	
}
