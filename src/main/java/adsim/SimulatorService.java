package adsim;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.*;
import adsim.core.IScenario;
import adsim.core.Simulator;
import adsim.misc.LoggingService;
import adsim.visualize.SimulatorFrame;

public class SimulatorService {
    private Simulator sim;
    private Future<?> token;

    public SimulatorService() {
        LoggingService.initLoggers();
    }

    public static SimulatorService startAsync(IScenario scenario) {
        val simsrv = new SimulatorService();
        simsrv.run(scenario);
        return simsrv;
    }

    public static void startAndReport(IScenario scenario, boolean visualize) {
        val simsrv = new SimulatorService();
        simsrv.run(scenario);
        if (visualize)
            simsrv.visualize();
        simsrv.joinFinished();
        simsrv.report();
    }

    public static void startAndReport(IScenario scenario) {
        SimulatorService.startAndReport(scenario, false);
    }

    public Future<?> run(IScenario scenario) {
        sim = new Simulator(scenario);
        return token = sim.start();
    }

    public void report() {
        sim.report();
    }

    public void joinFinished() {
        try {
            token.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void runAndWait(IScenario scenario) {
        run(scenario);
        joinFinished();
    }

    public void visualize() {
        val frame = new SimulatorFrame(sim);
        frame.setVisible(true);
    }

    // 0.0 - 1.0
    public double getProgress() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
