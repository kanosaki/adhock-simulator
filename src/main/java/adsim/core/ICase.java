package adsim.core;

import java.util.List;


public interface ICase {
    // nodes, defaultNodeHandler,
    List<Node> getNodes();
    
    String getName();
    
    long getStepLimit();
    
    ICaseReport report(Session session);
}
