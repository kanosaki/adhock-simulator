package adsim.misc;

import lombok.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class Signal<V> {
	private ConcurrentLinkedQueue<SignalHandler<V>> handlers;

	public Signal() {
		this.handlers = new ConcurrentLinkedQueue<SignalHandler<V>>();
	}

	public void register(SignalHandler<V> handler) {
		this.handlers.add(handler);
	}

	protected Iterable<SignalHandler<V>> getHandlers() {
		return this.handlers;
	}

	protected void dispatch(Object sender, SignalHandler<V> handler, V arg) {
		handler.run(sender, arg);
	}

	public void fire(final Object sender, final V arg) {
		for (val handler : this.getHandlers()) {
			this.dispatch(sender, handler, arg);
		}
	}
	
	public static <V> Signal<V> async(){
		return new Async<V>();
	}
	
	public static <V> Signal<V> sync(){
		return new Signal<V>();
	}

	public static class Async<V> extends Signal<V> {
		@Override
		public void fire(final Object sender, final V arg) {
			val threadpool = Executors.newCachedThreadPool();
			val self = this;
			threadpool.execute(new Runnable() {
				@Override
				public void run() {
					self.super_fire(sender, arg);
				}
			});
		}
		
		void super_fire(Object sender, V arg){
			super.fire(sender, arg);
		}
	}

}
