package adsim.core;

public interface ISimulator {
	void start(IScenario scenario);
	ISession getSession();
}
