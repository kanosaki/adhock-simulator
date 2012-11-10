package adsim;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.Well19937c;


public class Util {
    public static final boolean DEBUG = true;

    public static String getCodeInfo() {
        // [1] is current frame, [2] is caller's frame
        StackTraceElement codeInfo = Thread.currentThread().getStackTrace()[2];
        return codeInfo.toString();
    }

    private static GaussianRandomGenerator gaussianGenerator = new GaussianRandomGenerator(new Well19937c());

    public static double getGaussianPoint(double mu, double sigma) {
        return gaussianGenerator.nextNormalizedDouble() * sigma + mu;
    }
}
