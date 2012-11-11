package adsim;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import adsim.core.INodeHandler;
import adsim.core.Node;

public class Util {
    public static final boolean DEBUG = true;

    public static String getCodeInfo() {
        // [1] is current frame, [2] is caller's frame
        StackTraceElement codeInfo = Thread.currentThread().getStackTrace()[2];
        return codeInfo.toString();
    }

    private static GaussianRandomGenerator gaussianGenerator = new GaussianRandomGenerator(
            new Well19937c());

    public static double getGaussianPoint(double mu, double sigma) {
        return gaussianGenerator.nextNormalizedDouble() * sigma + mu;
    }

    public static Collection<INodeHandler> nodeHandlersClone(
            Collection<INodeHandler> handlers) {
        val ret = new ArrayList<INodeHandler>
                (handlers.size());
        for (val h : handlers) {
            ret.add(h.clone());
        }
        return ret;
    }

    public static Collection<Node> nodesClone(
            Collection<Node> handlers) {
        val ret = new ArrayList<Node>(handlers.size());
        for (val h : handlers) {
            ret.add(h.clone());
        }
        return ret;
    }
}
