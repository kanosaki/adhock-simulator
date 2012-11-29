package adsim.core;

import lombok.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import adsim.core.Message.TellNeighbors;
import adsim.core.Message.TellNeighbors.Entry;

public abstract class RelationWeightManager {
    protected Map<NodeID, Entry> entries;
    protected PriorityQueue<Entry> entryQueue;
    protected final int capacity;
    public static final int DEFAULT_WEIGHT = -1;

    protected abstract Comparator<Entry> getComparator();

    protected abstract boolean isIgnoreEntry(Entry entry);

    protected abstract Entry updateEntry(Entry one, Entry other);

    public RelationWeightManager(int capacity) {
        this.capacity = capacity;
        entryQueue = new PriorityQueue<Entry>(capacity, getComparator());
        // エントリ数が固定なのでLoadFactorは1fです
        entries = new HashMap<NodeID, Entry>(capacity, 1);
    }

    public void push(TellNeighbors packet) {
        for (val entry : packet.getEntries()) {
            if (entries.containsKey(entry.getSender())) {
                updateInfo(entry);
            } else {
                storeNew(entry);
            }
        }
    }

    private void storeNew(Entry entry) {
        if (isIgnoreEntry(entry))
            return;
        while (entries.size() >= capacity) {
            entries.remove(entryQueue.poll().getSender());
        }
        entries.put(entry.getSender(), entry);
        entryQueue.offer(entry);
    }

    private void updateInfo(Entry entry) {
        val old = entries.get(entry.getSender());
        val updated = updateEntry(old, entry);
        if (!old.equals(updated)) {
            entries.put(entry.getSender(), updated);
            entryQueue.remove(old);
            entryQueue.offer(updated);
        }
    }

    public int get(NodeID id) {
        val entry = entries.get(id);
        if (entry == null) {
            return DEFAULT_WEIGHT; // Returns default value
        } else {
            return entry.getWeight();
        }
    }

    public Message export(Node me) {
        val es = new LinkedList<Entry>();
        for (val e : entries.values()) {
            es.add(e.step());
        }
        return new TellNeighbors(es);
    }

    public static class Recent extends RelationWeightManager {
        public Recent(int capacity) {
            super(capacity);
        }

        @Override
        protected Comparator<Entry> getComparator() {
            return new Comparator<Entry>() {
                @Override
                public int compare(Entry o1, Entry o2) {
                    return o1.getTimestamp() < o2.getTimestamp() ? -1
                            : (o1.getTimestamp() > o2.getTimestamp() ? 1
                                    : 0);
                }
            };
        }

        @Override
        protected boolean isIgnoreEntry(Entry entry) {
            if (entry.getWeight() <= 0)
                return true;
            // entry が 持っているEntry達よりも古い場合
            if (entryQueue.peek() != null
                    && entry.getTimestamp() < entryQueue.peek().getTimestamp())
                return true;
            return false;
        }

        @Override
        protected Entry updateEntry(Entry one, Entry other) {
            if (one.getSender().equals(other.getSender()) // same sender
                    && one.getTimestamp() < other.getTimestamp()) { // other is
                                                                    // newer
                return other;
            } else {
                return one;
            }
        }
    }

    public static class Regular extends RelationWeightManager {
        public Regular(int capacity) {
            super(capacity);
        }

        @Override
        protected Comparator<Entry> getComparator() {
            return new Comparator<Message.TellNeighbors.Entry>() {
                @Override
                public int compare(Entry o1, Entry o2) {
                    return o1.getWeight() - o2.getWeight();
                }
            };
        }

        @Override
        protected boolean isIgnoreEntry(Entry entry) {
            if (entry.getWeight() <= 0)
                return true;
            if (entryQueue.peek() != null
                    && entry.getWeight() < entryQueue.peek().getWeight())
                return true;
            return false;
        }

        @Override
        protected Entry updateEntry(Entry one, Entry other) {
            if (one.getSender().equals(other.getSender())
                    && one.getTimestamp() < other.getTimestamp()) {
                return new Entry(one.getSender(), one.getWeight()
                        + other.getWeight(), other.getTimestamp());
            } else {
                return one;
            }
        }

    }
}
