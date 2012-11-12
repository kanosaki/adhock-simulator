package adsim;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import adsim.core.INodeHandler;
import adsim.core.Node;

public class Util {
    public static final boolean DEBUG = true;
    private static RandomGenerator random;
    private static GaussianRandomGenerator gaussianGenerator;
    static {
        random = new Well19937c();
        gaussianGenerator = new GaussianRandomGenerator(random);
    }

    public static String getCodeInfo() {
        // [1] is current frame, [2] is caller's frame
        StackTraceElement codeInfo = Thread.currentThread().getStackTrace()[2];
        return codeInfo.toString();
    }

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

    public static int randInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static <T> T randomSelect(List<T> lst, int min, int max) {
        return lst.get(randInt(min, max));
    }

    public static <T> T randomSelect(List<T> lst) {
        return lst.get(random.nextInt(lst.size()));
    }
}
