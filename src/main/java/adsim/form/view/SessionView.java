package adsim.form.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import adsim.core.ISession;

public class SessionView extends JPanel {
	private ISession model;

	/**
	 * Create the panel.
	 */
	public SessionView(ISession session) {
		setLayout(new BorderLayout(0, 0));
		
		FieldView fieldView = new FieldView(session.getField());
		add(fieldView, BorderLayout.CENTER);

	}

}
