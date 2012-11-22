package adsim.handler;

import lombok.*;
import java.util.Comparator;

import adsim.Util;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;
import adsim.core.Message.Envelope;

public class RecentKeepCollector extends NodeHandlerBase {

    @Override
    public void initialize(Node node) {
        // do nothing
    }

    @Override
    public void interval(Session sess, final Node node) {
        if (node.isBufferFilled()) {
            node.sortBuffer(new Comparator<Message.Envelope>() {
                @Override
                public int compare(Envelope o1, Envelope o2) {
                    val map = node.getWeightsMap();
                    val v1 = map.get(o1.getToId());
                    val v2 = map.get(o2.getToId());
                    return v1 - v2;
                }
            });
            while (node.isBufferFilled()) {
                node.disposeMessage(0);
            }
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {
        // do nothing
    }

}
