package adsim.core;

import lombok.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import adsim.handler.IHandlerSignal;

public class NodeTest {
    @Test
    public void test_differentId() {
        val node1 = new Node();
        val node2 = new Node();
        assertThat(node1.getId(), is(not(node2.getId())));
    }
    
    static enum DummySignals implements IHandlerSignal {
        
    }
    
    static class DummyDevice extends Device {
        @Override
        public void send(Message packet) {
            super.send(packet);
        }
        
        @Override
        public Message recv() {
            return super.recv();
        }
    }

}
