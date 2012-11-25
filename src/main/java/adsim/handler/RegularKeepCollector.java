package adsim.handler;

import lombok.*;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import adsim.Util;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.NodeID;
import adsim.core.RelationWeightManager;
import adsim.core.Session;
import adsim.core.Message.TellNeighbors;

public class RegularKeepCollector extends NodeHandlerBase {
    private RelationWeightManager accumlator;

    public RegularKeepCollector() {
        accumlator = new RelationWeightManager.Regular(Node.WEIGHT_BUFFER_MAX);
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
                    return accumlator.get(arg0.getToId())
                            - accumlator.get(arg1.getToId());
                }
            });
            while (node.isBufferFilled()) {
                node.disposeMessage(0);
            }
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {
        if (packet.getType() == Message.TYPE_TELLNEIGHBORS) {
            accumlator.push((TellNeighbors) packet);
        }
    }

}
