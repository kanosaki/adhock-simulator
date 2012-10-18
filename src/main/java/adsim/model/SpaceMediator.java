package adsim.model;

import adsim.*;
import lombok.*;

/**
 * 同一の空間、同一のチャンネルだと無線通信は混線するというルールをシミュレートします
 */
public class SpaceMediator {
	private final IField field;
	public SpaceMediator(@NonNull IField field){
		this.field = field;
	}
	
	
	public void addNode(INode node){
		
	}
	
	public void dispatchPacket(IPacket packet, INode sender) {
		
	}
	
	static class Channel {
		
	}
}
