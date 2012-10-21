package adsim.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import lombok.*;

import adsim.INode;
import adsim.SimulatorService;
import adsim.core.CompositeScenario;
import adsim.core.IScenario;
import adsim.core.ISession;
import adsim.defaults.Node;

public class FirstSample extends CompositeScenario {
	ISession session;

	public static void main(String[] args) {
		val simsrv = SimulatorService.create(new FirstSample());
		val win = simsrv.getWindow();
		win.setVisible(true);
		simsrv.start();
	}

	@Override
	public Iterable<INode> createNodes() {
		val node = new Node();
		return Arrays.asList(new INode[] { node });
	}
}
