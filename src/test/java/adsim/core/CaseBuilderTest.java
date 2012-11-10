package adsim.core;

import lombok.*;
import static org.junit.Assert.*;

import org.junit.Test;

import adsim.handler.VoidHandler;

public class CaseBuilderTest {

    @Test
    public void test_void() {
        val res = new CaseBuilder().done();
        assertTrue(res.isEmpty());
    }

    @Test
    public void test_one() {
        val res = new CaseBuilder()
                .name("Foobar")
                .nodes(2)
                .push()
                .done();
        assertTrue(res.size() == 1);
        val firstCase = res.get(0);
        assertEquals(firstCase.getName(), "Foobar");
        assertEquals(firstCase.getNodes().size(), 2);
    }

    @Test
    public void test_handler() {
        val res = new CaseBuilder()
                .name("Foobar")
                .handler(VoidHandler.get())
                .nodes(2)
                .push()
                .name("Hogehoge")
                .nodes(1)
                .push()
                .done();
        assertEquals(res.size(), 2);
        val firstCase = res.get(0);
        assertEquals(firstCase.getNodes().size(), 2);
        val fNodes = firstCase.getNodes();
        assertEquals(fNodes.get(0).getHandler(), VoidHandler.get());
        assertEquals(fNodes.get(1).getHandler(), VoidHandler.get());

        val secondCase = res.get(1);
        assertEquals(secondCase.getNodes().size(), 1);
        assertEquals(secondCase.getNodes().get(0).getHandler(), VoidHandler.get());
    }

}
