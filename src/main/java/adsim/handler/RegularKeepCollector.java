package adsim.handler;

import lombok.*;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import adsim.Util;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.NodeID;
import adsim.core.Session;
import adsim.core.Message.TellNeighbors;

public class RegularKeepCollector extends NodeHandlerBase {
    private Map<NodeID, Integer> accumulator;

    public RegularKeepCollector() {
        accumulator = new TreeMap<NodeID, Integer>();
    }

    @Override
    public void initialize(Node node) {
        // do nothing
    }

    @Override
    public void interval(Session sess, Node node) {
        if (node.isBufferFilled()) {
            node.sortBuffer(new Comparator<Message.Envelope>() {
                @Override
                public int compare(Message.Envelope arg0, Message.Envelope arg1) {
                    val dst0 = Util.mapGet(accumulator, arg0.getToId(), 0);
                    val dst1 = Util.mapGet(accumulator, arg1.getToId(), 0);
                    return dst0 - dst1; // Ascend
                }
            });
            while (node.isBufferFilled()) {
                node.disposeMessage(0);
            }
        }
    }

    private void updateEntry(TellNeighbors.Entry entry) {
        if (!accumulator.containsKey(entry.getSender())) {
            accumulator.put(entry.getSender(), entry.getWeight());
        } else {
            val sender = entry.getSender();
            val updatedValue = accumulator.get(sender) + entry.getWeight();
            accumulator.put(sender, updatedValue);
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {
        if (packet.getType() == Message.TYPE_TELLNEIGHBORS) {
            for (val entry : ((TellNeighbors) packet).getEntries()) {
                updateEntry(entry);
            }
        }
    }

}
