package adsim.core;

import adsim.handler.SignalArgs;

/*
 * Executes following actions:
 *  Node initialize(Initial messages, position)
 *  Enqueue relay message
 *  Republish message
 *  Move node
 *  
 */
public interface INodeHandler extends Cloneable, Comparable<INodeHandler> {
    void initialize(Node node);

    void interval(Session sess, Node node);

    void onReceived(Node self, Message packet);

    INodeHandler clone();

    int getPriority();

    String getName();

    /**
     * シグナルを処理します
     */
    void onSignal(String name, INodeHandler sender, SignalArgs arg);
}
