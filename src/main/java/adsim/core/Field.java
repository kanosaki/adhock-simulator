package adsim.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import lombok.*;

public class Field {
    public static final int MAX_SEND_COUNT = 100;
    private final @Getter
    SpaceMediator space = initSpace();
    private double size;
    private ArrayList<Device> devices;

    public List<Device> getDevices() {
        return this.devices;
    }

    public Field(double fieldSize) {
        this.size = fieldSize;
        this.devices = new ArrayList<Device>();
    }

    private SpaceMediator initSpace() {
        return new SpaceMediator(this);
    }

    public void addDevice(Device dev) {
        if (dev == null)
            throw new IllegalArgumentException("dev is null");
        this.devices.add(dev);
    }

    public void addDevices(Collection<Device> devs) {
        this.devices.addAll(devs);
    }

    public void next() {
        this.dispathPackets();
    }

    private void dispathPackets() { // FIXME
        for (int sent = 0; sent < MAX_SEND_COUNT; sent++) {
            for(val dev : getDevices()) {
                val request = dev.offerSend();
                if(request != null) {
                    space.dispatch(dev, request);
                }
            }
        }
    }

    public void expandCapacity(int size) {
        devices.ensureCapacity(devices.size() + size);
    }

}
