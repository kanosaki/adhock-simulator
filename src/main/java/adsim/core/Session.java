package adsim.core;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Session {
    private int DEFAULT_STEP_LIMIT = 100;
    private ICase cas;
    
    @Getter
    private long step;
    
    @Getter
    private long stepLimit;

    public List<Node> getNodes() {
        return cas.getNodes();
    }

    private @Getter
    @Setter(AccessLevel.PROTECTED)
    WorkerState state;

    private final @Getter
    Field field;

    public Session(ICase cas) {
        this.cas = cas;
        this.field = new Field();
        this.step = 0;
        this.stepLimit = cas.getStepLimit();
        stepCheck();
        log.debug("Session for " + cas.toString() + " initialized");
    }

    private void stepCheck() {
        if (stepLimit == 0) {
            log.warn("This case has no StepLimit, applying default limit");
            stepLimit = DEFAULT_STEP_LIMIT;
        }
    }

    public void next() {
        log.debug("Step " + this.step);
        step += 1;
        for (val node : cas.getNodes()) {
            node.next(this);
        }
        this.field.next();
    }
 
    public void start() {
        try {
            while (step < 100) { // TODO: REMOVE THIS GUARD
                next();
            }
        } catch (SessionFinishedException e) {
            log.info("Session finied.");
        }
    }
}
