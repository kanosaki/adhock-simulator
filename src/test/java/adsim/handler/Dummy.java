package adsim.handler;

import lombok.*;
import java.util.Collection;

import adsim.core.Device;
import adsim.core.ICase;
import adsim.core.Message;
import adsim.core.Node;
import adsim.core.Session;

public class Dummy {

    public static class DNode extends Node {

    }

    public static class DSession extends Session {
        public DSession(ICase cas) {
            super(cas);
        }
    }

    public static class DDevice extends Device {
        public static DDevice emulateReceived(Collection<Message> packets) {
            val dev = new DDevice();
            for (val packet : packets) {
                dev.pushPacket(packet);
            }
            return dev;
        }
    }
}
