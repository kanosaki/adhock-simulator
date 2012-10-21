package adsim.form.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import adsim.INode;

public class NodeView extends JComponent {
	private INode model;

	public NodeView(INode model) {
		this.model = model;
	}

	@Override
	protected void paintComponent(Graphics orig_g) {
		super.paintComponent(orig_g);
		Graphics2D g = (Graphics2D) orig_g;
		g.drawRect(0, 0, 10, 10);
	}

}
