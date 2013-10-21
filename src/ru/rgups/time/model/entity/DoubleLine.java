package ru.rgups.time.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "double")
public class DoubleLine {
	
	public DoubleLine(){
		
	}
	
	@Element(name = "predmet")
	private String title;
	
	@Element(name = "tip")
	private String type;
	
	@Element(name = "name_p")
	private String teacher;
	
	@Element(name = "kab")
	private String room;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
