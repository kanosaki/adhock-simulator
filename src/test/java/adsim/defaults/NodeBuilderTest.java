package adsim.defaults;

import lombok.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adsim.core.Device;

public class NodeBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_count(){
		val nodes = new NodeBuilder()
			.buildStart()
				.push()
			.buildStart()
				.push()
			.publish();
		assertEquals(2, nodes.size());
	}
	
	@Test
	public void test_vacant(){
		val nodes = new NodeBuilder().publish();
		assertEquals(0, nodes.size());
	}

	@Test
	public void test_nodes(){
		val nodes = new NodeBuilder()
			.buildStart()
				.radioPower(10.0)
				.push()
			.publish();
		val dev = (Device)nodes.get(0).getDevice();
		assertEquals(10.0, dev.getRadioPower(), 0.001);
	}
}
