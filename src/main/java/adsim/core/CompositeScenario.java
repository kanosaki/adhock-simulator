package adsim.core;

import lombok.*;
import java.util.ArrayList;

import adsim.INode;

public class CompositeScenario implements IScenario {
	@Setter
	private Iterable<INode> nodes;

	public Iterable<INode> getNodes() {
		if (this.nodes == null)
			return this.nodes = this.createNodes();
		else
			return this.nodes;
	}

	@Override
	public void init(ISession session) {
		for (val node : this.getNodes()) {
			session.addNode(node);
		}
	}

	public Iterable<INode> createNodes() {
		return new ArrayList<INode>();
	}

	@Override
	public void beforeStep() {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterStep() {
		// TODO Auto-generated method stub

	}

}
