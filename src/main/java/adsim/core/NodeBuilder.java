package adsim.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import lombok.*;

import adsim.handler.JumpMove;
import adsim.misc.Vector;

public class NodeBuilder {
    private ArrayList<INodeHandler> handlers;
    private ArrayList<Node> nodes;

    public NodeBuilder(int nodeCapacity) {
        handlers = new ArrayList<INodeHandler>();
        nodes = new ArrayList<Node>(nodeCapacity);
    }

    public NodeBuilder() {
        this(10); // JDK default capacity
    }

    public NodeBuilder startAt(Vector v) {
        handlers.add(new JumpMove(0, v));
        return this;
    }

    public NodeBuilder startAt(double x, double y) {
        return startAt(new Vector(x, y));
    }
    
    public NodeBuilder handler(INodeHandler handler) {
        handlers.add(handler);
        return this;
    }

    public NodeBuilder push(int count) {
        val handler = new CompositeNodeHandler(handlers).prune();
        for (int i = 0; i < count; i++) {
            nodes.add(new Node(handler.clone()));
        }
        return this;
    }

    public NodeBuilder push() {
        return push(1);
    }

    public Collection<Node> done() {
        return Collections.unmodifiableCollection(nodes);
    }
}
