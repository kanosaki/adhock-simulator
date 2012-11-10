package adsim.handler;

import adsim.core.INodeHandler;

public abstract class NodeHandlerBase implements INodeHandler {

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int compareTo(INodeHandler o) {
        return getPriority() == o.getPriority() ?
                0
                : ((getPriority() < o.getPriority())
                        ? -1
                        : 1);

    }
    
    @Override
    public void onSignal(String name, INodeHandler sender, Object arg) {
        // TODO Auto-generated method stub
        
    }

    public abstract INodeHandler clone();
}
