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
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    SimulatorService.start(new FirstSample());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected Collection<ICase> createCases() {
        return new CaseBuilder()
                .name("FirstSample")
                .step(100)
                .nodes(new NodeBuilder()
                        .startAt(0, 0)
                        .handler(new FIFOCollector())
                        .handler(new IntervalPublisher(10))
                        .handler(new FloodingReplayer())
                        .push(2)
                        .done())
                .push()
                .done();
    }

}
