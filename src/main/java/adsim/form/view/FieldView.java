package adsim.form.view;

import lombok.*;
import java.awt.Graphics;

import javax.swing.JPanel;

import adsim.core.IField;
import adsim.defaults.Field;
import adsim.misc.GraphicsAdapter;

public class FieldView extends JPanel {

    @Getter
    @Setter
    private double zoom;

    private IField model;

    public FieldView(IField model) {
        this.zoom = 10;
        this.model = model;
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
    }

    @Override
    protected void paintComponent(Graphics gc) {
    	super.paintComponent(gc);
    	val g = GraphicsAdapter.create(gc);
        for (val node : this.model.getDevices()) {
            val pos = node.getPosition();
            val r = (node.getRadioPower() / 2) * zoom;
            g.drawOval((int) (pos.getX() - r), (int) (pos.getY() - r), (int) (r * 2), (int) (r * 2));
        }
    }
}
