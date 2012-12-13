package adsim.handler;

import adsim.Util;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;
import adsim.misc.Vector;

public class RandomWalk extends NodeHandlerBase {

    @Override
    public void initialize(Node node) {

    }

    @Override
    public void interval(Session sess, Node node) {
        double length = Util.getGaussianPoint(10, 4);
        double theta = Math.random() * Math.PI * 2;
        Vector movevect = Vector.unit.lengthen(length).rotate(theta);
        node.move(movevect);
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

}
