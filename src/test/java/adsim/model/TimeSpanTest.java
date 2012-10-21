package adsim.model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adsim.TimeSpan;

public class TimeSpanTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_TimeSpan() {
	}
	
	@Test
	public void test_add(){
		TimeSpan ts1 = new TimeSpan(100);
		TimeSpan ts2 = new TimeSpan(200);
		TimeSpan expected = new TimeSpan(300);
		
		assertEquals(expected, ts1.add(ts2));
		assertEquals(ts1, new TimeSpan(100));
	}

}
