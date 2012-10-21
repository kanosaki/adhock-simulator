package adsim.core;

import java.util.List;


public interface ISimulationScheduler {

	void start(ISimulator sim, List<IScenario> scenarios);


}
