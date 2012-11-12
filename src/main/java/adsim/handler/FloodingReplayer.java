package adsim.handler;

import lombok.*;
import adsim.core.INodeHandler;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;

/**
 * 定期的にバッファのメッセージを順番に送信します。
 */
public class FloodingReplayer extends NodeHandlerBase {
    private int index;

    public FloodingReplayer() {
        this(0);
    }

    // Copy constructor
    private FloodingReplayer(int index) {
        this.index = index;
    }

    @Override
    public void initialize(Node node) {
        // Do nothing
    }

    @Override
    public void interval(Session sess, Node node) {
        val buffer = node.getBuffer();
        if (!buffer.isEmpty()) {
            val nextPointer = (index + 1) % buffer.size();
            node.broadcast(buffer.get(nextPointer));
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {
        // Do nothing

    }

    @Override
    public INodeHandler clone() {
        return new FloodingReplayer(index);
    }

}
