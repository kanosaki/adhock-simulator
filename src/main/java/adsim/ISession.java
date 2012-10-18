package adsim;

import java.util.Set;

import adsim.model.WorkerState;

/**
 * A simulator session.
 */
public interface ISession {
	Set<INode> getNodes();
	WorkerState getState();
	
}
