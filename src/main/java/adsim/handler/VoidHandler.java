package adsim.handler;

import adsim.core.INodeHandler;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;

public class VoidHandler extends NodeHandlerBase {
    private static final VoidHandler instance = new VoidHandler();

    public static VoidHandler get() {
        return instance;
    }

    /**
     * Use VoidHandler.get instead.
     */
    private VoidHandler() {

    }

    @Override
    public void initialize(Node node) {
    }

    @Override
    public void interval(Session sess, Node node) {
    }

    @Override
    public void onReceived(Node self, Message packet) {
    }

    @Override
    public INodeHandler clone() {
        return this;
    }
}
