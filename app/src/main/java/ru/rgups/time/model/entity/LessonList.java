package ru.rgups.time.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Collection;


@Root(name = "xml")
@DatabaseTable(tableName = "main_lesson_table")
public class LessonList {
	
	public final static String TABLE_NAME = "main_lesson_table";
	
	@DatabaseField(id = true)
	private int groupId;
	
	@Element(name = "nazvanie", required = false, type = String.class)
	private String title;
	
	@ElementList(inline = true, type = Day.class, required = false, empty = true)
	@ForeignCollectionField(eager = true)
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
//		PreferenceManager.getInstance().saveGroupId(Integer.toString(groupId));
	}
	

}
