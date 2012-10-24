package adsim.form.view;

import lombok.*;
import java.awt.Graphics;

import javax.swing.JPanel;

import adsim.core.Field;
import adsim.core.IField;

public class FieldView extends JPanel {

	private IField model;

	public FieldView(IField model) {
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
			g.drawOval((int) pos.getX(), (int) pos.getY(), 50, 50);
		}
	}
}
