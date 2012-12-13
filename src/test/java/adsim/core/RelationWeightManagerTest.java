package adsim.core;

import java.util.LinkedList;

import lombok.*;
import static org.junit.Assert.*;

import org.junit.Test;

import adsim.core.Message.TellNeighbors;
import adsim.core.Message.TellNeighbors.Entry;

public class RelationWeightManagerTest {

    public TellNeighbors createPacket(NodeID id, int weight, long timestamp) {
        val entries = new LinkedList<TellNeighbors.Entry>();
        entries.add(new Entry(id, weight, new LinkedList<Long>(), timestamp));
        return new TellNeighbors(entries);
    }

    @Test
    public void test_update() {
        val rwm = new RelationWeightManager.Recent(10);
        val id = new NodeID();
        val pack1 = createPacket(id, 3, 1);
        val pack2 = createPacket(id, 4, 2);
        rwm.push(pack1);
        rwm.push(pack2);
        assertEquals(4, rwm.get(id));
    }

    @Test
    public void test_ignore() {
        val rwm = new RelationWeightManager.Recent(10);
        val id = new NodeID();
        val pack1 = createPacket(id, 3, 2);
        val pack2 = createPacket(id, 4, 1);
        rwm.push(pack1);
        rwm.push(pack2);
        assertEquals(3, rwm.get(id));
    }

    @Test
    public void test_export() {
        val rwm = new RelationWeightManager.Recent(10);
        val id1 = new NodeID();
        val id2 = new NodeID();
        val pack1 = createPacket(id1, 3, 2);
        val pack2 = createPacket(id2, 4, 1);
        rwm.push(pack1);
        rwm.push(pack2);
        val result = (TellNeighbors) rwm.export(null);
        for (val entry : result.getEntries()) {
            if (entry.getSender().equals(id1)) {
                assertEquals(2, entry.getWeight());
                assertEquals(2, entry.getTimestamp());
            } else if (entry.getSender().equals(id2)) {
                assertEquals(3, entry.getWeight());
                assertEquals(1, entry.getTimestamp());
            }
        }
    }

    @Test
    public void test_collect() {
        val rwm = new RelationWeightManager.Recent(2);
        val id1 = new NodeID();
        val id2 = new NodeID();
        val id3 = new NodeID();
        rwm.push(createPacket(id1, 1, 1));
        rwm.push(createPacket(id3, 3, 3));
        rwm.push(createPacket(id2, 2, 2));
        assertEquals(3, rwm.get(id3));
        assertEquals(2, rwm.get(id2));
        assertEquals(RelationWeightManager.DEFAULT_WEIGHT, rwm.get(id1));
    }

}
