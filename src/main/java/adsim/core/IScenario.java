package adsim.core;

public interface IScenario {
	String getName();
	void init(ISession session);
}
