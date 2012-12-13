package adsim.core;

import lombok.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

import adsim.handler.FIFOCollector;
import adsim.handler.FloodingReplayer;
import adsim.handler.GatherMotion;
import adsim.handler.InfoSpreader;
import adsim.handler.IntervalPublisher;
import adsim.handler.RandomWalk;
import adsim.handler.RecentKeepCollector;
import adsim.handler.RegularKeepCollector;
import adsim.handler.RoundsMotion;

public class Case implements ICase {
    public static final int DEFAULT_STEP_LIMIT = 100;
    @Getter
    private int id;

    @Getter
    private int tryId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private long stepLimit;

    @Getter
    private double fieldSize;

    @Getter
    private int spreadStep;

    @Getter
    private CollectMode collectMode;

    @Getter
    private Motion motion;

    @Getter
    @Setter
    private int watchNodeCount;

    private int nodesCount;
    private INodeHandler nodeHandler;

    private ArrayList<Node> nodes;
    private final LinkedBlockingQueue<Object[]> result;

    public Case() {
        result = new LinkedBlockingQueue<Object[]>();
        spreadStep = 0;
        collectMode = CollectMode.FIFO;
        nodesCount = 50;
        this.fieldSize = 1000;
    }

    public Case(int id, int inTypeId, int nodesCount, double fieldSize,
            int spreadStep,
            CollectMode collectMode, Motion motion, int stepLimit,
            double publishPerStep) {
        this();
        this.id = id;
        this.tryId = inTypeId;
        this.stepLimit = stepLimit;
        this.fieldSize = fieldSize;
        this.spreadStep = spreadStep;
        this.collectMode = collectMode;
        this.motion = motion;
        this.nodesCount = nodesCount;
        // build handlers
        val handlers = new ArrayList<INodeHandler>();
        handlers.add(motion.toHandler());
        if (spreadStep > 0) {
            handlers.add(new InfoSpreader(spreadStep));
        }
        handlers.add(collectMode.toHandler());
        handlers.add(new FloodingReplayer());
        handlers.add(new IntervalPublisher(publishPerStep));
        this.nodeHandler = new CompositeNodeHandler(handlers).prune();
    }

    @Override
    public List<Node> getNodes() {
        if (nodes == null) {
            nodes = new ArrayList<Node>(nodesCount);
            for (int i = 0; i < nodesCount; i++) {
                nodes.add(new Node(nodeHandler.clone()));
            }
        }
        return nodes;
    }

    public void addNode(Node node) {
        if (nodes == null)
            nodes = new ArrayList<Node>();
        nodes.add(node);
    }

    public void addNodes(Collection<Node> nodes2) {
        if (nodes == null)
            nodes = new ArrayList<Node>();
        nodes.addAll(nodes2);
    }

    @Override
    public void tellResult(ResultReport report) {
        try {
            result.put(new Object[] {
                    id,
                    tryId,
                    nodesCount,
                    fieldSize,
                    collectMode,
                    motion,
                    spreadStep,
                    stepLimit,
                    report.getMessagesCreatedCount(),
                    report.getMessagesAcceptedCount(),
                    report.getPacketsSentCount(),
                    report.getPacketsDisposedCount()
            });
            nodes = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String
                .format(
                        "[Case Node: %d, Field: %f, Collect: %s, Motion: %s, Spread: %d]",
                        nodesCount, fieldSize, collectMode, motion, spreadStep);
    }

    @Override
    public Future<Object[]> getResult() {
        val f = new FutureTask<Object[]>(new Callable<Object[]>() {
            @Override
            public Object[] call() throws Exception {
                return result.take();
            }
        });
        f.run();
        return f;
    }

    public static enum Motion {
        RoundMotion, GatherMotion, RandomWalk;
        public INodeHandler toHandler() {
            switch (this) {
            case GatherMotion:
                return new GatherMotion();
            case RoundMotion:
                return new RoundsMotion();
            case RandomWalk:
                return new RandomWalk();
            default:
                throw new IllegalStateException();
            }
        }
    }

    public static enum CollectMode {

        FIFO, RecentKeep, RegularKeep;

        public INodeHandler toHandler() {
            switch (this) {
            case FIFO:
                return new FIFOCollector();
            case RecentKeep:
                return new RecentKeepCollector();
            case RegularKeep:
                return new RegularKeepCollector();
            default:
                throw new IllegalStateException();
            }
        }
    }
}
