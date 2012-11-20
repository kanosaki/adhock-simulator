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

    public void test_randomSelectExcept() {
        List<Integer> src = Arrays.asList(1, 2, 3, 4);
        val res = Util.randomSelectExcept(src, 1, 2, 3);
        assertEquals(res.intValue(), 1);

        val res2 = Util.randomSelectExcept(src, 3, 4);
        assertTrue(res.intValue() == 1 || res.intValue() == 2);
    }

}
