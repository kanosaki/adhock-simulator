package adsim.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.*;

public class Field {
    private final @Getter
    SpaceMediator space = initSpace();
    private ArrayList<Device> devices;

    public List<Device> getDevices() {
        return this.devices;
    }

    public Field() {
        this.devices = new ArrayList<Device>();
    }

    public Field(Collection<Device> initDevices) {
        this.devices = new ArrayList<Device>(initDevices);
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

    private void dispathPackets() {
        for (val dev : this.getDevices()) {
            Message sending = null;
            while ((sending = dev.offerSend()) != null) {
                space.dispatch(dev, sending);
            }
        }
    }

    public void expandCapacity(int size) {
        devices.ensureCapacity(devices.size() + size);
    }

}
