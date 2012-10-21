package adsim.core;

public interface IScenario {
	void init(ISession session);
	void beforeStep();
	void afterStep();
}
