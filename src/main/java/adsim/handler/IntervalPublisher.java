package adsim.handler;

import lombok.*;
import adsim.Util;
import adsim.core.INodeHandler;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;

public class IntervalPublisher extends NodeHandlerBase {
    private int interval;
    private int count;

    public IntervalPublisher(double publishPerStep) {
        this.interval = (int) Math.round(1.0 / publishPerStep);
        this.count = 0;
    }

    @Override
    public void initialize(Node node) {

    }

    private Node recRandomSelect(Node pivot, int depth) {
        if (depth == 0)
            return pivot;
        else
            return recRandomSelect(Util.randomSelect(pivot.getFriends()),
                    depth - 1);
    }

    private void pushNext(Node node) {
        val dstNode = recRandomSelect(node, 2);
        val msg = node.createMessage(dstNode);
        node.broadcast(msg);
    }

    @Override
    public void interval(Session sess, Node node) {
        if (count == 0) {
            pushNext(node);
        }
        count += 1;
        if (count == interval)
            count = 0;
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

    @Override
    public IntervalPublisher clone() {
        return (IntervalPublisher) super.clone();
    }

}
