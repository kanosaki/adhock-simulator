package adsim.form;

import javax.swing.JPanel;

import adsim.ISimulator;

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
	public SimView() {
		setLayout(new BorderLayout(0, 0));
		
		SimController simController = new SimController();
		add(simController, BorderLayout.EAST);
		
		SimInfoView simInfoView = new SimInfoView();
		add(simInfoView, BorderLayout.SOUTH);
		
		FieldView fieldView = new FieldView();
		add(fieldView, BorderLayout.CENTER);

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
