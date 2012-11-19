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
                    val map = node.getWeightMap();
                    val v1 = Util.mapGet(map, o1.getToId(), 0);
                    val v2 = Util.mapGet(map, o2.getToId(), 0);
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
