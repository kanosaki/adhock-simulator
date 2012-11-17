package adsim.example;

import java.awt.EventQueue;
import java.util.Collection;

import lombok.*;

import adsim.SimulatorService;
import adsim.core.CaseBuilder;
import adsim.core.CompositeNodeHandler;
import adsim.core.CompositeScenario;
import adsim.core.ICase;
import adsim.core.NodeBuilder;
import adsim.handler.FIFOCollector;
import adsim.handler.FloodingReplayer;
import adsim.handler.IntervalPublisher;

public class FirstSample extends CompositeScenario {
    public static void main(String[] args) {
        SimulatorService.start(new FirstSample());
    }

    public FirstSample() {
        super(createCases(), System.out);
    }

    protected static Collection<ICase> createCases() {
        return new CaseBuilder()
                .name("FirstSample")
                .step(100)
                .nodes(new NodeBuilder()
                        .startAt(0, 1)
                        .handler(new FIFOCollector(),
                                new IntervalPublisher(10),
                                new FloodingReplayer())
                        .push(1)
                        .startAt(0, 2)
                        .push(1)
                        .startAt(0, 3)
                        .push(1)
                        .done())
                .push()
                .done();
    }

}
