package adsim.handler;

import lombok.*;
import java.util.Comparator;

import adsim.core.Message;
import adsim.core.Node;
import adsim.core.RoundPoint;
import adsim.core.Session;
import adsim.misc.Vector;

public class GatherMotion extends NodeHandlerBase {
    private final double SLOWEST_MOVE_SPEED = 10.0;

    @Override
    public void initialize(Node node) {

    }

    private void updateDestination(Node node) {
        node.sortRoundPoints(new Comparator<RoundPoint>() {
            @Override
            public int compare(RoundPoint arg0, RoundPoint arg1) {
                return arg0.getRemainToNextMeeting()
                        - arg1.getRemainToNextMeeting();
            }
        });
        val nextPoint = node.getRoundPoints().get(0);
        if (node.getCurrentDestination() != nextPoint) {
            node.setCurrentDestination(nextPoint);
        }
    }

    @Override
    public void interval(Session sess, Node node) {
        if (node.getRoundPoints().size() < 2)
            return;
        updateDestination(node);
        RoundPoint dst = node.getCurrentDestination();
        Vector directVect = dst.getPoint()
                .sub(node.getLocation());
        if (directVect.getLength() < dst.getRemainToNextMeeting()
                * SLOWEST_MOVE_SPEED) {
            return;
        }
        if (directVect.equals(Vector.zero))
            return;
        Vector moveVect = directVect.scale(1.0 / ((double) dst
                .getRemainToNextMeeting()));
        node.move(moveVect);
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

}
