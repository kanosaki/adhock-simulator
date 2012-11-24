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
    public static Collection<ICase> buildCase(int tryPerCase,
            Param<Integer> nodeNums,
            Param<Double> fieldSizes, Param<Integer> spreadStepNums,
            Param<CollectMode> modes, Param<Integer> stepLimits,
            Param<Double> publishPerSteps) {
        int id = 0;
        val ret = new LinkedList<ICase>();
        for (val nodeCount : nodeNums) {
            for (val fieldSize : fieldSizes) {
                for (val spreadStepNum : spreadStepNums) {
                    for (val mode : modes) {
                        for (val stepLimit : stepLimits) {
                            for (val publishPerStep : publishPerSteps) {
                                for (int inTypeID = 0; inTypeID < tryPerCase; inTypeID++) {
                                    ret.add(new Case(id, inTypeID, nodeCount, fieldSize,
                                            spreadStepNum, mode, stepLimit,
                                            publishPerStep));
                                    id += 1;
                                }
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

}
