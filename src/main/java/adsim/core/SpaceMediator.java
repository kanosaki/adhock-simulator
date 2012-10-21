package adsim.core;

import java.util.ArrayList;

import adsim.*;
import lombok.*;

/**
 * 同一の空間、同一のチャンネルだと無線通信は混線するというルールをシミュレートします
 */
public class SpaceMediator {
	private final Field field;

	public SpaceMediator(Field field) {
		this.field = field;
	}

	public void addNode(INode node) {

	}

	private ArrayList<INode> getNodes() {
		return this.field.getNodes();
	}

	public void dispatch(INode srcnode, IPacket packet) {
		for (val fnode : this.getNodes()) {
			if (fnode.equals(srcnode))
				continue;
			val radius = srcnode.getRadioPower();
			val tgt_pos = fnode.getPosition();
			val src_pos = srcnode.getPosition();
			val distanceSq = tgt_pos.disatnceSquare(src_pos);
			if (distanceSq < radius) {
				fnode.pushPacket(packet);
			}
		}
	}
	
}
