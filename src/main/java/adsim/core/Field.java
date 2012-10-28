package adsim.core;

import java.util.ArrayList;
import java.util.List;

import adsim.misc.Vector;
import lombok.*;

public class Field implements IField {
	private final @Getter
	SpaceMediator space = initSpace();
	private ArrayList<Device> devices;

	private ISession session;

	@Override
	public List<Device> getDevices() {
		return this.devices;
	}

	public Field(ISession session) {
		this.session = session;
		this.devices = new ArrayList<Device>();
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
