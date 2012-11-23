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

    public IntervalPublisher(int interval) {
        this.interval = interval;
        this.count = 0;
    }

    @Override
    public void initialize(Node node) {

    }

    private void pushNext(Node node) {
        val destNode = Util.randomSelect(node.getFriends());
        val msg = node.createMessage(destNode);
        node.broadcast(msg);
    }

    @Override
    public void interval(Session sess, Node node) {
        if (count == interval) {
            pushNext(node);
            count = 0;
        }
        count += 1;
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

    @Override
    public IntervalPublisher clone() {
        return (IntervalPublisher) super.clone();
    }

}
