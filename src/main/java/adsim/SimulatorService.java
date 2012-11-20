package adsim;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.*;
import adsim.core.IScenario;
import adsim.core.Simulator;
import adsim.misc.LoggingService;

public class SimulatorService {
    public SimulatorService() {
        LoggingService.initLoggers();
    }

    public static SimulatorService startAsync(IScenario scenario) {
        val simsrv = new SimulatorService();
        simsrv.run(scenario);
        return simsrv;
    }
    
    public static void start(IScenario scenario) {
        val simsrv = new SimulatorService();
        simsrv.runAndWait(scenario);
    }

    public Future<?> run(IScenario scenario) {
        val sim = new Simulator(scenario);
        return sim.start();
    }

    public void runAndWait(IScenario scenario) {
        val future = run(scenario);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // 0.0 - 1.0
    public double getProgress() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
