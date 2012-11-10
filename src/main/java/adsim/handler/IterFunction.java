package adsim.handler;

import java.util.Iterator;

public abstract class IterFunction implements Iterable<Double> {

    public abstract double next();
    public abstract boolean hasNext();

    public static IterFunction constant(double value) {
        return new Constant(value);
    }

    @Override
    public Iterator<Double> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<Double> {
        @Override
        public boolean hasNext() {
            return IterFunction.this.hasNext();
        }

        @Override
        public Double next() {
            return IterFunction.this.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Constant extends IterFunction {
        private final double value;

        public Constant(double value) {
            this.value = value;
        }

        @Override
        public double next() {
            return value;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

    }
}
