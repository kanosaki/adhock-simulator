package adsim.core;

import lombok.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import adsim.report.Reporter;

public class CompositeScenario implements IScenario {
    @Setter
    private String name;

    @Getter
    private Collection<ICase> cases;

    private OutputStream output;

    public CompositeScenario(Collection<ICase> cases, OutputStream os) {
        this.cases = new ArrayList<ICase>(cases);
        this.output = os;
    }

    public String getName() {
        if (this.name == null)
            return this.name = this.getClass().getSimpleName();
        else
            return this.name;

    }

    @Override
    public void init(Simulator sim) {
    }

    @Override
    public void report() {
        val headers = new String[] {
                "ID", "TryID", "Nodes", "FieldSize", "CollectMode", "SpreadDepth", "StepCount", "Created Messages", "Received Messages", "Sent Packets", "Disposed Packets"
        };
        val reporter = new Reporter(headers);
        for (val cas : cases) {
            val resultF = cas.getResult();
            try {
                val result = resultF.get();
                reporter.add(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        val ps = new PrintStream(output);
        reporter.dump(ps);
        ps.close();
    }
}
