package adsim.defaults;

import lombok.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import adsim.core.Device;
import adsim.core.INode;
import adsim.core.INodeController;
import adsim.misc.Vector;

public class NodeBuilder {
	private ArrayList<INode> createdNodes;
	private INode buildingNode;
	private Device buildingDevice;

	public NodeBuilder() {
		this.createdNodes = new ArrayList<INode>();
	}

	public List<INode> publish() {
		return Collections.unmodifiableList(this.createdNodes);
	}

	public NodeBuilder buildStart(INode node, Device device) {
		buildingNode = node;
		buildingDevice = device;
		return this;
	}

	public NodeBuilder buildStart() {
		return this.buildStart(new Node(new Device()), new Device());
	}

	public NodeBuilder controller(INodeController nc) {
		this.buildingNode.addController(nc);
		return this;
	}

	public NodeBuilder at(Vector pos) {
		buildingDevice.setPosition(pos);
		return this;
	}

	public NodeBuilder radioPower(double radioPower) {
		buildingDevice.setRadioPower(radioPower);
		return this;
	}

	public NodeBuilder commit() {
		return NodeBuilder.this;
	}

	public NodeBuilder push(int count) {
		for (int i = 0; i < count; i++) {
			val newnode = buildingNode.clone();
			newnode.injectDevice(buildingDevice.clone());
			this.createdNodes.add(newnode);
		}
		return this;
	}

	public NodeBuilder push() {
		return this.push(1);
	}
}
