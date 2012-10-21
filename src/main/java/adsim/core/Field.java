package adsim.core;

import java.util.ArrayList;

import adsim.INode;
import adsim.Vector;
import lombok.*;

public class Field implements IField {
	private final @Getter
	SpaceMediator space = initSpace();

	private SessionBase session;

	ArrayList<INode> getNodes() {
		return this.session.getNodesRef();
	}

	public Field(SessionBase session) {
		this.session = session;
	}

	private SpaceMediator initSpace() {
		return new SpaceMediator(this);
	}

	public void addNode(INode node) {
		this.space.addNode(node);
	}

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
