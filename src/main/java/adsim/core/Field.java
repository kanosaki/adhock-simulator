package adsim.core;

import java.util.ArrayList;
import java.util.List;

import adsim.INode;
import adsim.misc.Vector;
import lombok.*;

public class Field implements IField {
	private final @Getter
	SpaceMediator space = initSpace();

	private ISession session;

	@Override
	public List<INode> getNodes() {
		return this.session.getNodes();
	}

	public Field(ISession session) {
		this.session = session;
	}

	private SpaceMediator initSpace() {
		return new SpaceMediator(this);
	}

	@Override
	public void addNode(INode node) {
		this.space.addNode(node);
	}

	@Override
	public void next() {
		this.dispathPackets();
	}

	private void dispathPackets() {
		for (val node : this.getNodes()) {
			val sq = node.getSendQueue();
			if (!sq.isEmpty()) {
				for (val packet : sq) {
					this.space.dispatch(node, packet);
				}
			}
		}
	}
	
}
