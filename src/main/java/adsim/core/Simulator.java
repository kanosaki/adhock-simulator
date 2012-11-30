package adsim.core;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import adsim.ScenarioBuilder;
import adsim.misc.Signal;
import adsim.misc.SignalHandler;

/**
 * ISimulator implementation.
 * 
 */
@Slf4j
public class Simulator {
    private IScenario scenario;

    private boolean isStopInvoked;

    private Engine engine;

    private long startTime;

    private Signal<Session> onSessionUpdatedSignal;

    protected void onSessionUpdated(Session sess) {
        if (onSessionUpdatedSignal != null) {
            onSessionUpdatedSignal.fire(this, sess);
        }
    }

    public void addOnSessionUpdatedHandler(SignalHandler<Session> handler) {
        if (handler == null)
            throw new IllegalArgumentException();
        if (onSessionUpdatedSignal == null)
            onSessionUpdatedSignal = new Signal.Sync<Session>();
        onSessionUpdatedSignal.register(handler);
    }

    // Threadpool for Engine
    private static ExecutorService threadpool = Executors.newFixedThreadPool(4);

    public Simulator(IScenario scenario) {
        this.isStopInvoked = false;
        this.scenario = scenario;
        this.init();
    }

    public void stop() {
        this.isStopInvoked = true;
    }

    public Future<?> start() {
        if (this.isStopInvoked)
            throw new IllegalStateException("This simulator already closed.");
        log.info(String.format("Starting simulator with scenario '%s'",
                scenario.getName()));
        startTime = System.currentTimeMillis();
        return this.engine.start();
    }

    private void init() {
        this.engine = new Engine();
    }

    /**
     * Called when Engine#run finished.
     */
    protected void onFinished() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info(String.format("Simulator finisied. in %dms(%dsec)",
                elapsedTime, elapsedTime / 1000));
    }

    public void report() {
        scenario.report();
    }

    /**
     * Called when Engine#run completed successfully.
     */
    protected void onCompleted() {

    }

    class Engine implements Runnable {
        List<Future<Object>> fTasks;

        public Future<?> start() {
            val tasks = new ArrayList<Callable<Object>>(scenario.getCases()
                    .size());
            val allCaseCount = scenario.getCases().size();
            for (val cas : scenario.getCases()) {
                tasks.add(new Callable<Object>() {
                    @Override
                    public Object call() {
                        val session = new Session(cas);
                        log.info(
                                String.format(
                                        "Starting Session %d of %d (%.2f%% Complete)",
                                        cas.getId() + 1,
                                        allCaseCount,
                                        ((double) (cas.getId()) / (double) allCaseCount) * 100.0));
                        onSessionUpdated(session);
                        session.start();
                        return null;
                    }
                });
            }
            try {
                fTasks = threadpool.invokeAll(tasks);
                threadpool.submit(this);
                return fTasks.get(fTasks.size() - 1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void run() {
            try {
                for (val f : fTasks) {
                    f.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            onFinished();
        }
    }
}
