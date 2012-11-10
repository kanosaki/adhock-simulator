package adsim;

import lombok.*;
import adsim.core.IScenario;
import adsim.core.Simulator;
import adsim.misc.LoggingService;

public class SimulatorService {
	public SimulatorService() {
		LoggingService.initLoggers();
	}

	public static SimulatorService start(IScenario scenario) {
		val simsrv = new SimulatorService();
		simsrv.run(scenario);
		return simsrv;
	}

	public void run(IScenario scenario) {
		val sim = new Simulator(scenario);
		sim.start();
	}
	
	// 0.0 - 1.0
	public double getProgress() {
	    throw new UnsupportedOperationException("Not implemented");
	}
}
