package adsim.core;


public interface IWorker {
	void start();
	void stop();
	void pause();
	void resume();
	boolean canPause();
	WorkerState getState();
}
