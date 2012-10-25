package adsim.form.view;

import lombok.*;
import java.awt.Graphics;

import javax.swing.JPanel;

import adsim.core.Field;
import adsim.core.IField;

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
        // TODO Auto-generated method stub
        super.paintChildren(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (val node : this.model.getNodes()) {
            val pos = node.getPosition();
            val r = (node.getRadioPower() / 2) * zoom;
            g.drawOval((int) (pos.getX() - r), (int) (pos.getY() - r), (int) (r * 2), (int) (r * 2));
        }
    }
}
