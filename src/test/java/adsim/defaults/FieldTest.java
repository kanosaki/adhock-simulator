package adsim.defaults;

import lombok.*;
import static org.junit.Assert.*;

import org.junit.Test;

import adsim.core.Device;
import adsim.core.IPacket;

public class FieldTest {

    @Test
    public void test_send() {
        val dev1 = new Device();
        val dev2 = new Device();
        val field = new Field();
        field.addDevice(dev1);
        field.addDevice(dev2);
        val packet = new DummyPacket();
        dev1.send(packet);
        field.next();
        val recieved = dev2.recv();
        assertNotNull(recieved);
        assertEquals(packet, recieved);
    }
    
    static class DummyPacket implements IPacket {
        
    }

}
