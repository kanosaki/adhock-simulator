package adsim.handler;

import adsim.core.INodeHandler;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;
import adsim.misc.Vector;

/**
 * あるフレームに指定された座標へジャンプします。0フレームを指定することで、 ノードの初期化時に位置設定を行えます
 */
public class JumpMove extends NodeHandlerBase {
    private final Vector destination;
    private final long triggerFrame;

    public JumpMove(long triggerAt, Vector destination) {
        this.triggerFrame = triggerAt;
        this.destination = destination;
    }

    @Override
    public void initialize(Node node) {
        if (triggerFrame == 0) {
            node.moveTo(destination);
        }
    }

    @Override
    public void interval(Session sess, Node node) {
        if (sess.getStep() == triggerFrame) {
            node.moveTo(destination);
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {

    }

    @Override
    public INodeHandler clone() {
        return this;
    }

}
