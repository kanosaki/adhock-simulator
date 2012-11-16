package adsim.core;

import lombok.*;
import adsim.Util;

public abstract class Message {
    public static final int TYPE_ENVELOPE = 1;
    public static final int TYPE_TELLNEIGHBORS = 2;

    @Getter
    protected final long id;

    public Message(long id) {
        this.id = id;
    }

    public Message() {
        this(Util.randLong());
    }

    public abstract int getType();

    public Message clone() {
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public static class Envelope extends Message {
        @Getter
        private final NodeID fromId;
        @Getter
        private final NodeID toId;

        public Envelope(NodeID from, NodeID to) {
            fromId = from;
            toId = to;
        }

        @Override
        public String toString() {
            return String.format("[Envelope from:%s to:%s]",
                    fromId.shortToString(), toId.shortToString());
        }

        @Override
        public int getType() {
            return TYPE_ENVELOPE;
        }
    }

    public static class TellNeighbors extends Message {
        @Override
        public int getType() {
            return TYPE_TELLNEIGHBORS;
        }

    }
}
