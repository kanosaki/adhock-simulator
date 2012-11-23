package adsim;

import lombok.*;
import java.util.Collection;
import java.util.LinkedList;

import adsim.core.Case;
import adsim.core.CaseBuilder;
import adsim.core.ICase;
import adsim.core.IScenario;
import adsim.core.Case.CollectMode;
import adsim.misc.Param;

public class ScenarioBuilder {
    public static Collection<ICase> buildCase(Param<Integer> nodeNums,
            Param<Double> fieldSizes, Param<Integer> spreadStepNums,
            Param<CollectMode> modes, Param<Integer> stepLimits,
            Param<Double> publishPerSteps) {
        val ret = new LinkedList<ICase>();
        for (val nodeCount : nodeNums) {
            for (val fieldSize : fieldSizes) {
                for (val spreadStepNum : spreadStepNums) {
                    for (val mode : modes) {
                        for (val stepLimit : stepLimits) {
                            for (val publishPerStep : publishPerSteps) {
                                ret.add(new Case(nodeCount, fieldSize,
                                        spreadStepNum, mode, stepLimit,
                                        publishPerStep));
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

}
