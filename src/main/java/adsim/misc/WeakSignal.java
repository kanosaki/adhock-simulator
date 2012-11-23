package adsim.misc;

import lombok.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * モデル用の弱参照イベントを実装します
 * 
 * @param <T>
 *            イベントの値
 */
public class WeakSignal<T> extends Signal<T> {
    public ArrayList<WeakReference<SignalHandler<T>>> handlers;

    public WeakSignal() {
        this.handlers = new ArrayList<WeakReference<SignalHandler<T>>>();
    }

    @Override
    @Synchronized
    public void register(SignalHandler<T> handler) {
        val wl = new WeakReference<SignalHandler<T>>(handler);
        this.handlers.add(wl);
    }

    // TODO: Check thread safety
    @Override
    @Synchronized
    public Iterable<SignalHandler<T>> getHandlers() {
        val ret = new ArrayList<SignalHandler<T>>(this.handlers.size());
        val live_handlers = new ArrayList<WeakReference<SignalHandler<T>>>(
                this.handlers.size());
        for (val handlerRef : this.handlers) {
            val hdr = handlerRef.get();
            if (hdr != null) {
                ret.add(hdr);
                live_handlers.add(handlerRef);
            }
        }
        this.handlers = live_handlers;
        return ret;
    }

    @Override
    public boolean hasHandler() {
        return !handlers.isEmpty();
    }
}
