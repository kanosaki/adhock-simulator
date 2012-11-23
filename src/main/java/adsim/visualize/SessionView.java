package adsim.visualize;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import adsim.core.Device;
import adsim.core.Field;
import adsim.core.Node;
import adsim.core.Session;
import adsim.handler.RoundsMotion;
import adsim.misc.GraphicsAdapter;
import adsim.misc.SignalHandler;
import adsim.misc.Vector;

public class SessionView extends JPanel {
    private static final long serialVersionUID = 1L;
    private Session model;
    private double scale = 0.5;

    public SessionView() {

    }

    public SessionView(Session model) {
        this.model = model;
        model.setInterval(100);
        model.addOnNextHandler(new SignalHandler<Long>() {
            @Override
            public void run(Object sender, Long val) {
                repaint();
            }
        });
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
            if (node.isVerbose()) {
                ga.setColor(Color.RED);
                for (Vector v : node.getRoundPoints()) {
                    ga.drawCircle(fixScale(v), RoundsMotion.POINT_RADIUS);
                }
            }
            ga.fillCircle(fixScale(dev.getPosition()), 3);
            ga.setColor(Color.BLACK);
        }
    }
}
