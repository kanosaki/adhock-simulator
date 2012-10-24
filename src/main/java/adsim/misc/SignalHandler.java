package adsim.misc;

public interface SignalHandler<E> {
	void run(Object sender, E val);
}
