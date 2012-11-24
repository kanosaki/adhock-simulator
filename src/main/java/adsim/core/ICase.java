package adsim.core;

import java.util.List;
import java.util.concurrent.Future;

public interface ICase {
    int getId();

    int getTryId();

    // nodes, defaultNodeHandler,
    List<Node> getNodes();

    String getName();

    long getStepLimit();

    double getFieldSize();

    Future<Object[]> getResult();

    void tellResult(ResultReport report);

    int getWatchNodeCount();
}
