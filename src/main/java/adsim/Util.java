package adsim;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import adsim.core.INodeHandler;
import adsim.core.Node;
import adsim.misc.Vector;

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

    public static double getReflexiveGaussianPoint(double mu, double sigma,
            double min, double max) {
        if (max < min) {
            throw new ArithmeticException();
        }
        double v = getGaussianPoint(mu, sigma);
        while (v < min || max < v) {
            v = getGaussianPoint(mu, sigma);
        }
        return v;
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

    public static int randInt(int min, int max) {
        // nextInt(n) returns 0 <= x < n
        return random.nextInt(max - min + 1) + min;
    }

    public static long randLong() {
        return random.nextLong();
    }

    public static <T> T randomSelect(List<T> lst, int min, int max) {
        if (lst.size() == 0)
            return null;
        return lst.get(randInt(min, max));
    }

    public static <T> T randomSelect(List<T> lst) {
        if (lst.size() == 0)
            return null;
        return lst.get(random.nextInt(lst.size()));
    }

    public static <T> boolean contains(T[] xs, T a) {
        for (T x : xs) {
            if (x == null)
                continue;
            if (x.equals(a))
                return true;
        }
        return false;
    }

    public static <T> T randomSelectExcept(List<T> src, T... excepts) {
        // 無限ループを回避するためexpectsに含まれない要素が存在するか確かめます。
        boolean anotherContains = false;
        for (T one : src) {
            if (!Util.contains(excepts, one)) {
                anotherContains = true;
                break;
            }
        }
        if (!anotherContains) {
            throw new IllegalArgumentException(
                    "randomSelectExceptでは、選択元のリストに除く要素以外の要素が含まれる必要があります");
        }
        while (true) {
            T one = Util.randomSelect(src);
            boolean checkok = true;
            for (T check : excepts) {
                if (one.equals(check)) {
                    checkok = false;
                }
            }
            if (checkok)
                return one;
        }
    }

    public static <K, V> V mapGet(Map<K, V> map, K key, V defaultValue) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return defaultValue;
        }
    }

    public static boolean doubleCompare(double x, double y, double epsilon) {
        return (x - epsilon < y) && (y < x + epsilon);
    }

    public static Vector createRandomPoint(double size) {
        return new Vector(
                Util.getReflexiveGaussianPoint(size / 2, size / 3, 0, size),
                Util.getReflexiveGaussianPoint(size / 2, size / 3, 0, size));
    }
}
