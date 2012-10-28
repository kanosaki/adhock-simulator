package adsim.core;

import lombok.*;
import java.util.ArrayList;


public class CompositeScenario implements IScenario {
	@Setter
	private Iterable<INode> nodes;
	
	@Setter
	private String name;
	
	public String getName(){
		if(this.name == null)
			return this.name = this.getClass().getSimpleName();
		else
			return this.name;
			
	}

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

	/**
	 * Override this method to configure initial nodes.
	 */
	public Iterable<INode> createNodes() {
		throw new IllegalStateException("Override this method or set nodes field");
	}
}
