package adsim.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import adsim.core.Device;
import adsim.core.IField;
import adsim.core.IPacket;
import adsim.core.SpaceMediator;
import adsim.misc.Vector;
import lombok.*;

public class Field implements IField {
	private final @Getter
	SpaceMediator space = initSpace();
	private ArrayList<Device> devices;

	@Override
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

	@Override
	public void addDevice(Device dev) {
		if (dev == null)
			throw new IllegalArgumentException("dev is null");
		this.devices.add(dev);
	}

	@Override
	public void next() {
		this.dispathPackets();
	}

	private void dispathPackets() {
		for (val dev : this.getDevices()) {
			IPacket sending = null;
			while ((sending = dev.offerSend()) != null) {
				space.dispatch(dev, sending);
			}
		}
	}

}
