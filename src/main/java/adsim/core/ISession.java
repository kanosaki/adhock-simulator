package adsim.core;

import java.util.List;


public interface ISession {

	WorkerState getState();

	void addNode(INode node);
	
	List<INode> getNodes();

	void next();

	void reset();
}