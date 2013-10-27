package ru.rgups.time.model.entity;

import org.simpleframework.xml.Root;
@Root(name = "nad", strict = false)
public class OverLine extends LessonInformation{

	public OverLine(){
		
	}

	@Override
	public void setShift(int shift) {
		this.setShift(2);
	}
	
	
	
	
	
}
