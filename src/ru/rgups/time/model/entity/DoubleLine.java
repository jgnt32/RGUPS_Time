package ru.rgups.time.model.entity;

import org.simpleframework.xml.Root;

@Root(name = "double")
public class DoubleLine extends LessonInformation{
	
	public DoubleLine(){
	}

	@Override
	public void setShift(int shift) {
		this.setShift(0);
	}
	
	
	
}
