package adsim.core;

import java.util.Queue;

import adsim.misc.Vector;

/**
 * Logical model for Node
 */
public interface INode {
    Vector getPosition();

    void setPosition(Vector pos);

    double getRadioPower();

    void setRadioPower(double radioPower);

    void pushPacket(IPacket packet);

    Queue<IPacket> getSendQueue();

    void next();

    void addController(INodeController nc);
    
    INode clone();

}
