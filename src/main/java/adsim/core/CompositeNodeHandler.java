package adsim.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import adsim.handler.NodeHandlerBase;
import adsim.handler.SignalArgs;
import adsim.handler.VoidHandler;

import lombok.*;

public class CompositeNodeHandler extends NodeHandlerBase {
    private PriorityQueue<INodeHandler> handlers;

    public CompositeNodeHandler(Collection<INodeHandler> initHandlers) {
        handlers = new PriorityQueue<INodeHandler>(initHandlers);
    }

    public CompositeNodeHandler() {
        handlers = new PriorityQueue<INodeHandler>();
    }

    public void addHandler(INodeHandler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }

    @Override
    public void initialize(Node node) {
        for (val h : handlers) {
            h.initialize(node);
        }
    }

    @Override
    public void interval(Session sess, Node node) {
        for (val h : handlers) {
            h.interval(sess, node);
        }
    }

    @Override
    public void onReceived(Node self, Message packet) {
        for (val h : handlers) {
            h.onReceived(self, packet);
        }
    }

    public INodeHandler clone() {
        synchronized (handlers) {
            val newhandlers = new ArrayList<INodeHandler>(handlers.size());
            for (val h : handlers) {
                newhandlers.add(h.clone());
            }
            return new CompositeNodeHandler(newhandlers);
        }
    }

    /**
     * 再帰的にINodeHandlerを抽出します。ただし、VoidHandlerは除かれます
     */
    private List<INodeHandler> getValidHandlers() {
        val ret = new LinkedList<INodeHandler>();
        for (val hdr : handlers) {
            if (hdr instanceof VoidHandler)
                continue;
            if (hdr instanceof CompositeNodeHandler) {
                ret.addAll(((CompositeNodeHandler) hdr).getValidHandlers());
            } else {
                ret.add(hdr);
            }
        }
        return ret;
    }

    /**
     * 無駄なINodeHandlerを再帰的に除去します、VoidHandlerは除去され、
     * 入れ子になったComposteNodeHandlerから再帰的にINodeHandlerを
     * 取り出し一つのCompisiteNodeHandlerにします
     * 
     * @return INodeHandlerを一つも持たない場合VoidHandler、1つしかINodeHandlerを持たない場合、
     *         そのINodeHandlerを、複数持つ場合はそれらを持ったCompositeNodeHandlerを返します
     */
    public INodeHandler prune() {
        val hdrs = getValidHandlers();
        if (handlers.size() == 0) {
            return VoidHandler.get();
        } else if (handlers.size() == 1) {
            return handlers.poll();
        } else {
            return new CompositeNodeHandler(hdrs);
        }
    }
    
    @Override
    public void onSignal(String name, INodeHandler sender, SignalArgs arg) {
        for(val nh : handlers) {
            nh.onSignal(name, sender, arg);
        }
    }

}
