package adsim.model;

import adsim.IField;
import lombok.*;

public class Field implements IField {
	private final @Getter SpaceMediator space = initSpace();
	
	private SpaceMediator initSpace(){
		return new SpaceMediator(this);
	}
}
