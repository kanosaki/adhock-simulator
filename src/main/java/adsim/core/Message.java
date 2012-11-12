package adsim.core;

import lombok.*;

@Data
public class Message {
    public Message(NodeID src, NodeID dst) {
        this.sourceId = src;
        this.destinationId = dst;
    }

    private NodeID sourceId;
    private NodeID destinationId;

    public Message clone() {
        return this;
    }
}
