package adsim.handler;

import java.util.List;

import lombok.*;
import adsim.Util;
import adsim.core.INodeHandler;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.RoundPoint;
import adsim.core.Session;
import adsim.misc.Vector;

public class RoundsMotion extends NodeHandlerBase {
    public static final String SIGNAL_ARRIVE_POINT = "RoundsMotion/ArrivePoint";
    public static final double POINT_RADIUS = 10;

    private int waitCount;

    @Override
    public void initialize(Node node) {

    }

    private void updateDestination(Session sess, Node node) {
        RoundPoint nextPoint = Util.randomSelectExcept(node.getRoundPoints(),
                node.getCurrentDestination());
        node.setCurrentDestination(nextPoint);
    }

    private void setRandomWait(Session sess, Node node) {
        waitCount = (int) Util.getReflexiveGaussianPoint(300, 200, 0, 600);
    }

    private void moveAction(Session sess, Node node) {
        // 現在地から目的地までのベクトルを求めます
        Vector directVect = node.getCurrentDestination().getPoint()
                .sub(node.getLocation());
        // 求めたベクトルがゼロ、つまり、現在地と目的地が一致している場合は何もしません
        if (directVect.equals(Vector.zero))
            return;
        // 移動距離係数の乱数を発生させます
        double length = Util.getGaussianPoint(5, 2);
        double theta = Util.getGaussianPoint(0, Math.PI / 6); // 30
        // 実際に移動するベクトルを求めます
        Vector moveVect = directVect.lengthen(length).rotate(theta);
        // 移動
        node.move(moveVect);
    }

    @Override
    public void interval(Session sess, Node node) {
        if (node.getRoundPoints().size() < 2)
            return;

        if (waitCount > 0) {
            waitCount -= 1;
            return;
        }

        double distanceToDestination = node.getCurrentDestination().getPoint()
                .distance(
                        node.getLocation());
        // 目標地点に着いていると判定できる場合は、目的地を次の地点へ変更します
        if (distanceToDestination < POINT_RADIUS) {
            updateDestination(sess, node);
            setRandomWait(sess, node);
        } else {
            moveAction(sess, node);
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

    public INodeHandler clone() {
        return new RoundsMotion();
    }

}
