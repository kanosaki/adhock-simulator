package adsim.core;

import java.util.List;

import adsim.INode;

public interface IField {

	public abstract SpaceMediator getSpace();

	public abstract void next();

	public abstract void addNode(INode node);

	public abstract List<INode> getNodes();

}
