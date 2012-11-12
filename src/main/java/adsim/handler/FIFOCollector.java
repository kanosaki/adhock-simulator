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

    }

    @Override
    public void onReceived(Node self, Message packet) {
        if (self.isBufferFilled()) {
            self.disposeMessage(0);
        }
        self.pushMessage(packet);
    }

    public INodeHandler clone() {
        return this; // Currently, this class is immutable.
    }

}
