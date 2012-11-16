package adsim.core;

import lombok.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import adsim.handler.NodeHandlerBase;
import adsim.handler.SignalArgs;

public class NodeTest {
    @Test
    public void test_differentId() {
        val node1 = new Node();
        val node2 = new Node();
        assertThat(node1.getId(), is(not(node2.getId())));
    }

    @Test
    public void test_signals() {
        val dhB = new DummyHandlerSignalB();
        val node = new Node(new DummyHandlerSignalA(), dhB);
        assertFalse(dhB.isReceived());
        node.next(null);
        assertTrue(dhB.isReceived());
    }

    static class DummyHandlerSignalA extends NodeHandlerBase {
        @Override
        public void initialize(Node node) {

        }

        @Override
        public void interval(Session sess, Node node) {
            node.fireSignal("test/foobar", this);
        }

        @Override
        public void onReceived(Node self, Message packet) {

        }
    }
    
    static class DummyHandlerSignalB extends DummyHandlerSignalA {
        @Getter
        private boolean received;

        @Override
        public void onSignal(String name, INodeHandler sender, SignalArgs arg) {
            if (name.equals("test/foobar")) {
                received = true;
            }
        }
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
