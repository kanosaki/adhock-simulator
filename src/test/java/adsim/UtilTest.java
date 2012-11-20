package adsim;

import java.util.Arrays;
import java.util.List;

import lombok.*;
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
        assertThat(
                Util.getCodeInfo(),
                RegexMatcher
                        .matches("adsim\\.UtilTest\\.test_getCodeInfo\\(UtilTest\\.java:\\d+\\)"));
    }

    @Test
    public void test_randomSelectExcept() {
        List<Integer> src = Arrays.asList(1, 2, 3, 4);
        val res = Util.randomSelectExcept(src, 1, 2, 3);
        assertEquals(4, res.intValue());

        val res2 = Util.randomSelectExcept(src, 3, 4);
        assertTrue(res2.intValue() == 1 || res2.intValue() == 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_randomSelectExcept_fail() {
        List<Integer> src = Arrays.asList(1, 2, 3, 4);
        Util.randomSelectExcept(src, 1, 2, 3, 4);
    }

}
