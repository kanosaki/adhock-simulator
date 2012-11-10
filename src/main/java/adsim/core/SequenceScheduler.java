package adsim.core;

import lombok.*;
import java.util.List;


public class SequenceScheduler implements ISimulationScheduler {

    public Simulator createSimulator(IScenario scenario) {
        return new Simulator(scenario);
    }
    
	@Override
	public void start(List<IScenario> scenarios) {
		for(val scenario : scenarios){
		    val sim = createSimulator(scenario);
		    sim.start();
		}
	}


}
