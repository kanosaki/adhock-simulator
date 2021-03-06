package adsim.defaults;

import lombok.*;
import static org.junit.Assert.*;

import org.junit.Test;

import adsim.core.Device;
import adsim.core.Field;
import adsim.core.Message;
import adsim.core.NodeID;
import adsim.misc.Vector;

public class FieldTest {

    @Test
    public void test_send() {
        val dev1 = new Device(1);
        val dev2 = new Device(1);
        val field = new Field(100);
        field.addDevice(dev1);
        field.addDevice(dev2);
        val packet = new DummyPacket();
        dev1.send(packet);
        field.next();
        val recieved = dev2.recv();
        assertNotNull(recieved);
        assertEquals(packet, recieved);
    }

    @Test
    public void test_not_recieve() {
        val dev1 = new Device(1);
        val dev2 = new Device(1);
        val dev3 = new Device(1);
        dev2.setPosition(new Vector(0, 2));
        dev3.setPosition(new Vector(1, 0));
        val field = new Field(100);
        field.addDevice(dev1);
        field.addDevice(dev2);
        field.addDevice(dev3);
        val packet = new DummyPacket();
        dev1.send(packet);
        field.next();
        assertEquals(null, dev2.recv());
        assertEquals(packet, dev3.recv());
    }

    static class DummyPacket extends Message {
        public DummyPacket() {
            super();
        }

        @Override
        public int getType() {
            return -1;
        }
    }

}
