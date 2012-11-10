package adsim;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getCodeInfo() {
		assertThat(Util.getCodeInfo(),
				RegexMatcher.matches("adsim\\.UtilTest\\.test_getCodeInfo\\(UtilTest\\.java:\\d+\\)"));
	}

}
