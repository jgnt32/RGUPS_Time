package ru.rgups.time.model.entity;

import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;

@Root(name = "day", strict = false)
public class Day {
		

	@Element(name = "nomer_d")
	private int number;
	
	@ElementList(inline = true, type = Lesson.class)
	private Collection<Lesson> lessons;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Collection<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(Collection<Lesson> lessons) {
		this.lessons = lessons;
	}
	
	

}
