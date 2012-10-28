package adsim.defaults;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import adsim.core.Field;
import adsim.core.IField;
import adsim.core.IScenario;
import adsim.core.ISession;
import adsim.core.ISimulator;
import adsim.core.SessionFinishedException;

/**
 * ISimulator implementation.
 * 
 */
@Slf4j
public class Simulator implements ISimulator {
	private @Getter
	ISession session;

	private IScenario scenario;

	private boolean isStopInvoked;

	private Engine engine;

	private static final int MAX_STEPS = 100;

	public Simulator() {
		this.isStopInvoked = false;
	}

	public void stop() {
		this.isStopInvoked = true;
	}

	@Override
	public void start(IScenario scenario) {
		if (this.isStopInvoked)
			throw new IllegalStateException("This simulator already closed.");
		log.info(String.format("Starting simulator with scenario '%s'", scenario.getName()));
		this.scenario = scenario;
		this.init();
		this.engine.start();
	}

	private void init() {
		this.session = new Session(this.scenario);
		this.engine = new Engine();
	}

	class Engine implements Runnable {
		int step;

		public void start() {
			val threadpool = Executors.newCachedThreadPool();
			threadpool.execute(this);
		}

		@Override
		public void run() {
			try {
				while (!isStopInvoked) {
					// Guard for debugging
					// TODO Remove this break
					if (step > MAX_STEPS) {
						log.warn("Simulator stopped because of debugging guard.");
						break;
					}
					session.next();
					step += 1;
				}
			} catch (SessionFinishedException sfe) {

			}
		}
	}
}
