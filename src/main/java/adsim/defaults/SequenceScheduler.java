package adsim.defaults;

import java.util.List;
import lombok.*;
import adsim.core.IScenario;
import adsim.core.ISimulationScheduler;
import adsim.core.ISimulator;

public class SequenceScheduler implements ISimulationScheduler {

	@Override
	public void start(ISimulator sim, List<IScenario> scenarios) {
		for(val scenario : scenarios){
			sim.start(scenario);
		}
	}


}
