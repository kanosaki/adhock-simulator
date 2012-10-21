package adsim.defaults;

import lombok.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import adsim.core.IScenario;
import adsim.core.ISession;
import adsim.core.ISimulator;
import adsim.core.SessionFinishedException;

public class Simulator implements ISimulator {
	private ISession session;
	private IScenario scenario;
	private boolean isStopInvoked;
	private Engine engine;
	private static final int MAX_STEPS = 100;

	public Simulator() {
		this.isStopInvoked = false;
		this.engine = new Engine();
	}

	public void stop() {
		this.isStopInvoked = true;
	}

	@Override
	public void start(IScenario scenario) {
		this.scenario = scenario;
		this.session = new Session(scenario);
		this.engine.start();
	}

	class Engine implements Runnable {
		int step;

		public void start() {
			val exec_srv = Executors.newCachedThreadPool();
			exec_srv.execute(this);
		}

		@Override
		public void run() {
			try{
				while (!isStopInvoked && step < MAX_STEPS) {
					session.next();
					step += 1;
				}	
			} catch(SessionFinishedException sfe){
				
			}
		}
	}
}
