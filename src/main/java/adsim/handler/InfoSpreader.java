package adsim.handler;

import lombok.*;
import adsim.core.Message;
import adsim.core.Message.TellNeighbors;
import adsim.core.Node;
import adsim.core.Session;

public class InfoSpreader extends NodeHandlerBase {
    @Getter
    private int depth;

    public InfoSpreader(int depth) {
        this.depth = depth;
    }

    @Override
    public void initialize(Node node) {

    }

    @Override
    public void interval(Session sess, Node node) {
        val packet = (TellNeighbors)node.getWeightsMap().export(node);
        packet.add(node.getId(), depth, node.getRecentReceived());
        node.broadcast(packet);
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

}
