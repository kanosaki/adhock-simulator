package adsim.misc;

import lombok.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/**
 * イベントを実装します、registerでイベントハンドラを登録し、fireで実際にイベントを発行します。
 * ファクトリメソッドSignal.syncを用いた場合はfire呼び出しと同期的に、
 * Signal.asyncを用いた場合はfire呼び出しと非同期的にイベントが発行されます。
 * 
 * @param <V>
 *            イベントの値
 */
public abstract class Signal<V> {

	public abstract void register(SignalHandler<V> handler);

	public abstract Iterable<SignalHandler<V>> getHandlers();

	protected void dispatch(Object sender, SignalHandler<V> handler, V arg) {
		handler.run(sender, arg);
	}

	public void fire(final Object sender, final V arg) {
		for (val handler : this.getHandlers()) {
			this.dispatch(sender, handler, arg);
		}
	}

	public static <V> Signal<V> async() {
		return new Async<V>();
	}

	public static <V> Signal<V> sync() {
		return new Sync<V>();
	}

	public static class Sync<V> extends Signal<V> {
		private ConcurrentLinkedQueue<SignalHandler<V>> handlers;

		public Sync() {
			this.handlers = new ConcurrentLinkedQueue<SignalHandler<V>>();
		}

		public void register(SignalHandler<V> handler) {
			this.handlers.add(handler);
		}

		public Iterable<SignalHandler<V>> getHandlers() {
			return this.handlers;
		}
	}

	public static class Async<V> extends Sync<V> {
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

		void super_fire(Object sender, V arg) {
			super.fire(sender, arg);
		}
	}

}
