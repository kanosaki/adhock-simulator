package adsim.core;

import adsim.INode;

public interface ISession {

	WorkerState getState();

	Field getField();

	void addNode(INode node);

	void next();

	void reset();

	void init();

}