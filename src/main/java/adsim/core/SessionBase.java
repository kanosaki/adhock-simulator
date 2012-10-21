package adsim.core;

import java.util.ArrayList;

import lombok.*;

import adsim.INode;

public abstract class SessionBase implements ISession {
	private final ArrayList<INode> nodes;
	private IScenario scenario;

	ArrayList<INode> getNodesRef() {
		return this.nodes;
	}

	private @Getter
	@Setter(AccessLevel.PROTECTED)
	WorkerState state;

	private final @Getter
	Field field;

	public SessionBase(IScenario scenario) {
		this.scenario = scenario;
		this.nodes = new ArrayList<INode>();
		this.field = new Field(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see adsim.model.ISession#addNode(adsim.INode)
	 */
	@Override
	public void addNode(INode node) {
		this.nodes.add(node);
		this.field.addNode(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see adsim.model.ISession#next()
	 */
	@Override
	public void next() {
		for (val node : this.nodes) {
			node.next();
		}
		this.field.next();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see adsim.model.ISession#reset()
	 */
	@Override
	public void reset() {

	}

	@Override
	public void init() {
		this.scenario.init(this);
	}

}
