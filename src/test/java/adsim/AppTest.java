package adsim;

import lombok.*;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test_ExecParams() {
        val argLine = "-i foobar.xml -o hogehoge.csv 50,100,150:1000.0:0,3:fifo,recent,regular:1000:0.01 1";
        val args = argLine.split(" ");
        try {
            val params = new App.ExecParams(args);
            assertEquals("foobar.xml", params.getInputFile());
            assertEquals("hogehoge.csv", params.getOutputFile());
        } catch (ParseException e) {
            fail();
        }
    }
}
