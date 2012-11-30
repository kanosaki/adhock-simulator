package adsim.visualize;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import adsim.core.Device;
import adsim.core.Field;
import adsim.core.Node;
import adsim.core.Session;
import adsim.core.Simulator;
import adsim.handler.RoundsMotion;
import adsim.misc.GraphicsAdapter;
import adsim.misc.SignalHandler;
import adsim.misc.Vector;

public class SessionView extends JPanel {
    private static final long serialVersionUID = 1L;
    private Session model;
    private double scale = 0.5;
    private Set<Node> watchNodes;

    public SessionView() {

    }

    public SessionView(Simulator sim) {
        sim.addOnSessionUpdatedHandler(new SignalHandler<Session>() {
            @Override
            public void run(Object sender, Session val) {
                if (model != null && !model.isFinished())
                    return;
                scale = 500.0 / val.getField().getSize();
                purgePrevSession();
                linkSession(val);
            }
        });
        this.watchNodes = new HashSet<Node>();
    }

    private void updateWatchNodes() {
        if (model == null)
            return;
        // steal verbose flag to prevent long long log output.
        watchNodes.clear();
        for (Node node : model.getNodes()) {
            if (node.isVerbose()) {
                watchNodes.add(node);
                node.setVerbose(false);
            }
        }
    }

    private void purgePrevSession() {
        if (model == null)
            return;
        model.setInterval(0);
        model.clearOnNextHandler();
    }

    private void linkSession(Session sess) {
        model = sess;
        model.setInterval(20);
        model.addOnNextHandler(new SignalHandler<Long>() {
            @Override
            public void run(Object sender, Long val) {
                repaint();
            }
        });
        updateWatchNodes();
    }

    private Vector fixScale(Vector v) {
        return v.getNorm() == 0 ? v
                : v.scale(scale);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GraphicsAdapter ga = new GraphicsAdapter(g);
        if (model == null)
            return;
        Field field = model.getField();
        for (Node node : model.getNodes()) {
            Device dev = node.getDevice();
            if (watchNodes.contains(node)) {
                ga.setColor(Color.RED);
                for (Vector v : node.getRoundPoints()) {
                    ga.drawCircle(fixScale(v), RoundsMotion.POINT_RADIUS
                            * scale);
                }
            }
            ga.fillCircle(fixScale(dev.getPosition()), 3);
            ga.setColor(Color.BLACK);
        }
    }
}
