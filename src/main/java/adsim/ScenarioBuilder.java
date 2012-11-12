package adsim;

import adsim.core.CaseBuilder;
import adsim.core.IScenario;

public class ScenarioBuilder {
    private CaseBuilder caseBuilder;

    public CaseBuilder buildCase() {
        return caseBuilder = new CaseBuilder();
    }
}
