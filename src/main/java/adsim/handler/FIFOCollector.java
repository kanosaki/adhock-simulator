package adsim.handler;

import lombok.*;
import adsim.core.INodeHandler;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;

public class FIFOCollector extends NodeHandlerBase {

    @Override
    public void initialize(Node node) {
    }

    @Override
    public void interval(Session sess, Node node) {
        while (node.isBufferFilled()) {
            node.disposeMessage(0);
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

    public INodeHandler clone() {
        return this; // Currently, this class is immutable.
    }

}
