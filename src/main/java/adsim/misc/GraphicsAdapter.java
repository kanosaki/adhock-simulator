package adsim.misc;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import lombok.Delegate;

public class GraphicsAdapter {

    @Delegate(types = Graphics2D.class)
    private final Graphics2D g;

    public GraphicsAdapter(Graphics gc) {
        this(gc, true);
    }

    public GraphicsAdapter(Graphics gc, boolean enableAntiAlias) {
        g = (Graphics2D) gc;
        if (enableAntiAlias)
            this.setAitiAliasing();
    }

    public static GraphicsAdapter create(Graphics g) {
        return new GraphicsAdapter(g);
    }

    public void setAitiAliasing() {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void drawOval(double x, double y, double width, double height) {
    }

    public void drawCircle(Vector center, double radius) {
        int x = (int) Math.round(center.getX() - radius);
        int y = (int) Math.round(center.getY() - radius);
        int side = (int) Math.round(radius * 2);
        g.drawOval(x, y, side, side);
    }

    public void fillCircle(Vector center, double radius) {
        int x = (int) Math.round(center.getX() - radius);
        int y = (int) Math.round(center.getY() - radius);
        int side = (int) Math.round(radius * 2);
        g.fillOval(x, y, side, side);
    }
}
