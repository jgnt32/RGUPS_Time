package ru.rgups.time.model.entity;

import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root(name = "xml")
public class LessonList {
	
	private int groupId;
	
	@Element(name = "nazvanie")
	private String title;
	
	@ElementList(inline = true, type = Day.class)
	private Collection<Day> days;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Day> getDays() {
		return days;
	}

	public void setDays(Collection<Day> days) {
		this.days = days;
	}
	

}
