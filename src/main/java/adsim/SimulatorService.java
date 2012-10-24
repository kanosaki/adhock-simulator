package adsim;

import java.util.ArrayList;

import lombok.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import adsim.core.IScenario;
import adsim.core.ISimulationScheduler;
import adsim.core.ISimulator;
import adsim.defaults.SequenceScheduler;
import adsim.defaults.Simulator;
import adsim.form.SimulatorMainWindow;

public class SimulatorService {
	private ArrayList<IScenario> senarios;
	private ISimulator sim;
	private SimulatorMainWindow window;

	public SimulatorService() {
		this.senarios = new ArrayList<IScenario>();
		this.sim = this.createSimulator();
	}

	public static SimulatorService start(IScenario scenario) {
		val simsrv = new SimulatorService();
		simsrv.addScenario(scenario);
		simsrv.start();
		return simsrv;
	}

	protected ISimulator createSimulator() {
		return new Simulator();
	}

	public void addScenario(IScenario scenario) {
		this.senarios.add(scenario);
	}

	public void start() {
		this.start(new SequenceScheduler());
	}

	public void start(ISimulationScheduler scheduler) {
		scheduler.start(this.sim, this.senarios);
	}

	public JFrame getWindow() {
		if (this.window == null)
			return this.window = this.createWindow();
		else
			return this.window;
	}

	private SimulatorMainWindow createWindow() {
		return new SimulatorMainWindow(this.sim);
	}

	public JPanel getPanel() {
		throw new RuntimeException("Not Implemented" + Util.getCodeInfo());
	}
}
