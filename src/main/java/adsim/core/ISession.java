package adsim.core;

import java.util.List;

import adsim.INode;

public interface ISession {

	WorkerState getState();

	void addNode(INode node);
	
	List<INode> getNodes();

	void next();

	void reset();
}