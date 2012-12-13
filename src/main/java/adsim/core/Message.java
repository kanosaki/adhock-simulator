package adsim.core;

import lombok.*;
import lombok.experimental.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
        private final LinkedList<Node> path;

        public Envelope(NodeID from, NodeID to) {
            fromId = from;
            toId = to;
            path = new LinkedList<Node>();
        }

        public void addPath(Node node) {
            path.add(node);
        }

        public void printPath() {
            System.out.format("------ PATH for %s -------\n", this);
            for (val node : path) {
                System.out.println(node.getId());
            }
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
        @Getter
        private final List<Entry> entries;

        public TellNeighbors(List<Entry> entries) {
            this.entries = entries;
        }

        public void add(Entry entry) {
            entries.add(entry);
        }

        public void add(NodeID id, int distance, List<Long> receivedEnvelopes) {
            val entry = new Entry(id, distance, receivedEnvelopes,
                    new Date().getTime());
            entries.add(entry);
        }

        @Override
        public int getType() {
            return TYPE_TELLNEIGHBORS;
        }

        public static TellNeighbors createSingle(NodeID id, int distance) {
            val entries = new LinkedList<Entry>();
            entries.add(new Entry(id, distance, new LinkedList<Long>(),
                    new Date().getTime()));
            return new TellNeighbors(entries);
        }

        @Value
        public static class Entry {
            private final NodeID sender;
            private final int weight;
            private final List<Long> receivedEnvelopes;
            private final long timestamp;

            public Entry step() {
                return new Entry(sender, weight - 1, receivedEnvelopes,
                        timestamp);
            }

            public Entry update(Entry other) {
                if (other.getSender().equals(sender)
                        && other.getTimestamp() > timestamp) {
                    return other;
                } else {
                    return this;
                }
            }
        }

        public String prettyPrint() {
            val sb = new StringBuilder();
            sb.append("[TellNaighbors :");
            for (val e : getEntries()) {
                sb.append(" ");
                sb.append(e.sender.shortToString());
                sb.append("-");
                sb.append(e.weight);
            }
            sb.append("]");
            return sb.toString();
        }
    }
}
