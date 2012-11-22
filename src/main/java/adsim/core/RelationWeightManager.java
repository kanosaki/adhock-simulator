package adsim.core;

import lombok.*;

import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;

import adsim.core.Message.TellNeighbors;
import adsim.core.Message.TellNeighbors.Entry;

public class RelationWeightManager {
    private SortedMap<NodeID, Entry> entries;
    private final int capacity;

    public RelationWeightManager(int capacity) {
        this.capacity = capacity;
        entries = new TreeMap<NodeID, Entry>();
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
        while (entries.size() >= capacity) {
            entries.remove(entries.lastKey());
        }
        if (entry.getWeight() <= 0)
            return;
        entries.put(entry.getSender(), entry);
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
            return 0; // Returns default value
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
