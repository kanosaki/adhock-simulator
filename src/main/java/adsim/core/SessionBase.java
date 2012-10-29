package adsim.core;

import java.util.ArrayList;
import java.util.List;

import adsim.defaults.Field;

import lombok.*;

public abstract class SessionBase implements ISession {
	private final ArrayList<INode> nodes;
	private IScenario scenario;

	public List<INode> getNodes() {
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
		this.field = new Field();
		this.scenario.init(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see adsim.model.ISession#addNode(adsim.INode)
	 */
	@Override
	public void addNode(INode node) {
		this.nodes.add(node);
		this.field.addDevice((Device) node.getDevice());
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
}
