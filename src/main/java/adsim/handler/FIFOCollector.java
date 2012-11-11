package adsim.handler;

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

    }

    @Override
    public void onReceived(Node self, Message packet) {
        if (self.isBufferFilled())
            self.getBuffer().poll();
        self.pushPacket(packet);
    }

    public INodeHandler clone() {
        return this; // Currently, this class is immutable.
    }

}