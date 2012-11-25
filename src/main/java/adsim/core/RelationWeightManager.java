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

public class RelationWeightManager {
    private Map<NodeID, Entry> entries;
    private PriorityQueue<Entry> entryQueue;
    private final int capacity;
    public static final int DEFAULT_WEIGHT = -1;

    public RelationWeightManager(int capacity) {
        this.capacity = capacity;
        entryQueue = new PriorityQueue<Entry>(capacity,
                new Comparator<Entry>() {
                    @Override
                    public int compare(Entry o1, Entry o2) {
                        return o1.getTimestamp() < o2.getTimestamp() ? -1
                                : (o1.getTimestamp() > o2.getTimestamp() ? 1
                                        : 0);
                    }
                });
        entries = new HashMap<NodeID, Entry>((int) (capacity * 1.6));
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
        if (entry.getWeight() <= 0)
            return;
        // entry が 持っているEntry達よりも古い場合
        if (entryQueue.peek() != null
                && entry.getTimestamp() < entryQueue.peek().getTimestamp())
            return;
        while (entries.size() >= capacity) {
            entries.remove(entryQueue.poll().getSender());
        }
        entries.put(entry.getSender(), entry);
        entryQueue.add(entry);
    }

    private void updateInfo(Entry entry) {
        val old = entries.get(entry.getSender());
        val updated = old.update(entry);
        if (!old.equals(updated)) {
            entries.put(entry.getSender(), updated);
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
}
