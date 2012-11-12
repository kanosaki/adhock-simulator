package adsim.core;

import lombok.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class NodeIDTest {

    @Test
    public void test() {
        val id1 = new NodeID();
        val id2 = id1.clone();
        assertTrue(id1.equals(id2));
        assertTrue(id1 != id2);
    }

}
