package adsim.misc;

import lombok.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Param<T> implements Iterable<T> {

    public static <E> Param<E> enumerate(E... vals) {
        return new Enumerate<E>(Arrays.asList(vals));
    }

    public static Param<Integer> range(int start, int end, int step) {
        val vals = new LinkedList<Integer>();
        for (int i = start; i <= end; i += step) {
            vals.add(i);
        }
        return new Enumerate<Integer>(vals);
    }

    public static class Enumerate<T> extends Param<T> {

        private Collection<T> values;

        public Enumerate(Collection<T> vals) {
            values = vals;
        }

        @Override
        public Iterator<T> iterator() {
            return values.iterator();
        }
    }
}
