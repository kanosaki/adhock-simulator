package adsim;

import java.util.Set;

import adsim.model.WorkerState;

/**
 * A simulator session.
 * ICase configuration + Simulation state control
 */
public interface ISession {
	ICase getCase();
	Set<INode> getNodes();
	WorkerState getState();
	
}
