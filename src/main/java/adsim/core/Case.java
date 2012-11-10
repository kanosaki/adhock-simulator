package adsim.core;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Case implements ICase {
    @Getter
    @Setter
    private String name;
    
    @Getter @Setter
    private long stepLimit;
    
    private ArrayList<Node> nodes;

    public Case() {
        nodes = new ArrayList<Node>();
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    @Override
    public ICaseReport report(Session session) {
        // TODO Auto-generated method stub
        return null;
    }

    public void addNodes(Collection<Node> nodes2) {
        nodes.addAll(nodes2);
    }
}
