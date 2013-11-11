package ru.rgups.time.model.entity;

import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@Root(name = "xml")
@DatabaseTable(tableName = "main_lesson_table")
public class LessonList {
	
	public final static String TABLE_NAME = "main_lesson_table";
	
	@DatabaseField(id = true)
	private int groupId;
	
	@Element(name = "nazvanie")
	private String title;
	
	@ElementList(inline = true, type = Day.class)
	@ForeignCollectionField(eager = false)
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	

}
