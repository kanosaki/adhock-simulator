package adsim.form.view;

import javax.swing.JPanel;

import adsim.core.ISimulator;

import java.awt.BorderLayout;
import lombok.*;

/**
 * Main view for simulator
 */
public class SimView extends JPanel {
	private @Getter ISimulator model;
	
	/**
	 * Create the panel.
	 */
	public SimView(ISimulator model) {
		this.model = model;
		setLayout(new BorderLayout(0, 0));
		
		SimController simController = new SimController();
		add(simController, BorderLayout.EAST);
		
		SimInfoView simInfoView = new SimInfoView();
		add(simInfoView, BorderLayout.SOUTH);
		
		
		SessionView sessionView = new SessionView(model.getSession());
		add(sessionView, BorderLayout.CENTER);
	}

	protected void onModelUpdated(ISimulator newModel) {
		
	}
	
	public void setModel(ISimulator model){
		if(!this.model.equals(model)){
			this.model = model;
			this.onModelUpdated(model);
		}
	}
}
