package adsim.core;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import adsim.handler.VoidHandler;

import lombok.*;

public class CaseBuilder {
    private ArrayList<ICase> result;
    private Case currentCase;
    private INodeHandler currentHandler;

    public CaseBuilder() {
        result = new ArrayList<ICase>();
        currentCase = new Case();
    }

    public CaseBuilder push() {
        if (currentCase != null) {
            result.add(currentCase);
        }
        currentCase = new Case();
        return this;
    }

    public CaseBuilder name(String name) {
        currentCase.setName(name);
        return this;
    }

    public CaseBuilder step(long stepLimit) {
        currentCase.setStepLimit(stepLimit);
        return this;
    }

    public CaseBuilder handler(INodeHandler handler) {
        currentHandler = handler;
        return this;
    }

    public CaseBuilder nodes(int count, INodeHandler handler) {
        for (int i = 0; i < count; i++) {
            val node = new Node(handler.clone());
            currentCase.addNode(node);
        }
        return this;
    }

    public CaseBuilder nodes(Collection<Node> nodes) {
        currentCase.addNodes(nodes);
        return this;
    }

    public CaseBuilder nodes(int count) {
        return nodes(count, currentHandler == null ? VoidHandler.get()
                : currentHandler);
    }
    
    public List<ICase> done() {
        return Collections.unmodifiableList(result);
    }
}
