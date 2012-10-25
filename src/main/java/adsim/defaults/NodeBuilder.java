package adsim.defaults;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import adsim.INode;
import adsim.core.INodeController;
import adsim.misc.Vector;

public class NodeBuilder {
    private ArrayList<INode> createdNodes;
    private INode buildingNode;

    public NodeBuilder() {
        this.createdNodes = new ArrayList<INode>();
    }

    public List<INode> publish() {
        return Collections.unmodifiableList(this.createdNodes);
    }

    public NodeBuilder buildStart(INode node) {
        this.buildingNode = node;
        return this;
    }

    public NodeBuilder buildStart() {
        return this.buildStart(new Node());
    }

    public NodeBuilder at(Vector pos) {
        this.buildingNode.setPosition(pos);
        return this;
    }

    public NodeBuilder radioPower(double radioPower) {
        this.buildingNode.setRadioPower(radioPower);
        return this;
    }

    public NodeBuilder controller(INodeController nc) {
        this.buildingNode.addController(nc);
        return this;
    }

    public NodeBuilder push(int count) {
        for (int i = 0; i < count; i++) {
            this.createdNodes.add(this.buildingNode.clone());
        }
        return this;
    }
    
    public NodeBuilder push() {
        return this.push(1);
    }

}
