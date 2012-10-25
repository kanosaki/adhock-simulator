package adsim.example;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import lombok.*;

import adsim.SimulatorService;
import adsim.core.CompositeScenario;
import adsim.core.INode;
import adsim.core.IScenario;
import adsim.core.ISession;
import adsim.defaults.Node;
import adsim.defaults.NodeBuilder;
import adsim.misc.Vector;

public class FirstSample extends CompositeScenario {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				val simsrv = SimulatorService.start(new FirstSample());
				val win = simsrv.getWindow();
				win.setVisible(true);
			}
		});
	}

	@Override
	public Iterable<INode> createNodes() {
		return new NodeBuilder()
		    .buildStart()
		        .at(new Vector(50, 50))
		        .radioPower(10)
		        .push()
		    .buildStart()
		        .at(new Vector(100, 100))
		        .radioPower(5)
		        .push()
		    .publish();
	}
}
