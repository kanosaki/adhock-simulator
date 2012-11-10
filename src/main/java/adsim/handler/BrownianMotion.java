package adsim.handler;


import adsim.core.INodeHandler;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;

public class BrownianMotion extends NodeHandlerBase {
    public BrownianMotion(double mu, double sigmaSquare) {
    }

    @Override
    public void initialize(Node node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void interval(Session sess, Node node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReceived(Node self, Message packet) {
        // TODO Auto-generated method stub

    }

    public INodeHandler clone() {
        throw new UnsupportedOperationException();
    }

}
