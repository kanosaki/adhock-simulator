package adsim.core;

import java.util.UUID;

import lombok.Delegate;
import lombok.experimental.Value;

@Value
public class NodeID {
    @Delegate(types = UUID.class)
    private final UUID id;

    public NodeID() {
        this.id = UUID.randomUUID();
    }

    private NodeID(UUID id) {
        this.id = id;
    }

    public NodeID clone() {
        return new NodeID(id);
    }
    
    public String shortToString() {
        return id.toString().substring(0, 8);
    }
}
