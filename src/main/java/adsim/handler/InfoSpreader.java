package adsim.handler;

import lombok.*;
import adsim.core.Message;
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

}
