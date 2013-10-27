package ru.rgups.time.model.entity;

import org.simpleframework.xml.Root;

@Root(name = "pod", strict = false)
public class UnderLine extends LessonInformation{
	
	public UnderLine(){
		
	}

	@Override
	public void setShift(int i) {
		this.setShift(1);
	}
	
	
	
}
