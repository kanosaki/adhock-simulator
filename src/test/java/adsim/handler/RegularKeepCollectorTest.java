package adsim.handler;

import java.util.Arrays;

import lombok.*;
import static org.junit.Assert.*;

import org.junit.Test;

import adsim.core.Message;
import adsim.core.Message.TellNeighbors;
import adsim.core.NodeID;
import adsim.handler.Dummy.DDevice;

public class RegularKeepCollectorTest {

    @Test
    public void test_collect() {
        val dnode = new Dummy.DNode();
        dnode.setBufferMax(2);
        val nid1 = new NodeID();
        val nid2 = new NodeID();
        val nid3 = new NodeID();
        val packets = Arrays.asList(new TellNeighbors[] {
                TellNeighbors.createSingle(nid2, 1),
                TellNeighbors.createSingle(nid1, 3),
                TellNeighbors.createSingle(nid3, 2),
        });
        val collector = new RegularKeepCollector();
        for (val packet : packets) {
            collector.onReceived(dnode, packet);
        }
        Message pack1;
        Message pack2;
        Message pack3;
        dnode.injectDevice(DDevice.emulateReceived(Arrays.asList(new Message[] {
                pack2 = new Message.Envelope(nid2, nid3),
                pack3 = new Message.Envelope(nid3, nid1),
                pack1 = new Message.Envelope(nid1, nid2),
        })));
        dnode.next(null); // push packets
        assertEquals(3, dnode.getBuffer().size());
        collector.interval(null, dnode);
        assertEquals(2, dnode.getBuffer().size());
        assertFalse(dnode.getBuffer().contains(pack1));
        assertTrue(dnode.getBuffer().contains(pack2));
        assertTrue(dnode.getBuffer().contains(pack3));
    }
}
