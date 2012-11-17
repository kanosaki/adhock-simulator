package adsim.core;

import java.util.Collection;

public interface IScenario {
    String getName();

    Collection<ICase> getCases();

    void init(Simulator sim);

    void report();
}
