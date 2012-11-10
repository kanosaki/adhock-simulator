package adsim.core;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ISimulator implementation.
 * 
 */
@Slf4j
public class Simulator {
    private IScenario scenario;

    private boolean isStopInvoked;

    private Engine engine;

    private static final int MAX_STEPS = 100;

    // Threadpool for Engine
    private static ExecutorService threadpool = Executors.newCachedThreadPool();

    public Simulator(IScenario scenario) {
        this.isStopInvoked = false;
        this.scenario = scenario;
        this.init();
    }

    public void stop() {
        this.isStopInvoked = true;
    }

    public void start() {
        if (this.isStopInvoked)
            throw new IllegalStateException("This simulator already closed.");
        log.info(String.format("Starting simulator with scenario '%s'", scenario.getName()));
        this.engine.start();
    }

    private void init() {
        this.engine = new Engine();
    }

    public IScenarioReport report() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Called when Engine#run finished.
     */
    protected void onFinished() {
    }

    /**
     * Called when Engine#run completed successfully.
     */
    protected void onCompleted() {

    }

    class Engine implements Runnable {

        int step;

        public void start() {
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
                    for (val cas : scenario.getCases()) {
                        new Session(cas).start();
                    }
                    step += 1;
                }
                onCompleted();
            } catch (SessionFinishedException sfe) {
                log.info("Session finisied.");
            } finally {
                onFinished();
            }
        }
    }
}
