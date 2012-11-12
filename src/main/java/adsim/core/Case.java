package adsim.core;

import lombok.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import adsim.report.TextCaseReport;

public class Case implements ICase {
    @Getter
    @Setter
    private String name;
    
    @Getter @Setter
    private long stepLimit;
    
    private ICaseReport reporter;
    
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
        return reporter;
    }

    public void addNodes(Collection<Node> nodes2) {
        nodes.addAll(nodes2);
    }

    public void textReport(OutputStream out) {
        reporter = new TextCaseReport(out);
    }
}
