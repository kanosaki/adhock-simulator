package adsim.handler;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import adsim.core.INodeHandler;

/**
 * INodeHandlerの共通部分の実装です。
 */
@Slf4j
public abstract class NodeHandlerBase implements INodeHandler {
    private String className;

    /**
     * NodeHandlerの名前を返します。デフォルトではクラス名が返されます。
     */
    @Override
    public String getName() {
        if (className == null) {
            className = this.getClass().getSimpleName();
        }
        return className;
    }

    /**
     * このNodeHandlerの実行優先順位です。数値が大きいほど他のNodeHandlerよりも早く実行されます。デフォルトでは0なので、
     * 他のNodeHandlerよりも早く実行させたい場合は正の値を、後に実行させたい場合は負の値を返すようにオーバーライドして下さい。
     */
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int compareTo(INodeHandler o) {
        return getPriority() == o.getPriority() ?
                0
                : ((getPriority() < o.getPriority())
                        ? -1
                        : 1);

    }

    /**
     * Signalとは、同じNodeを操作するNodeHandler同士で通信を行うためのシステムです。発生したSignalは、
     * 同じNodeのNodeHandlerのみに通知されます。
     */
    @Override
    public void onSignal(String name, INodeHandler sender, SignalArgs arg) {
    }

    public INodeHandler clone() {
        try {
            return (NodeHandlerBase) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
