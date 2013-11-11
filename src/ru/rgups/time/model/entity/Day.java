package ru.rgups.time.model.entity;

import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@Root(name = "day", strict = false)
@DatabaseTable(tableName = "day_table")
public class Day {
	
	public final static String TABLE_NAME = "day_table";
		
	@DatabaseField (foreign = true, columnName = "lessonList_id")
	private LessonList list;

	@Element(name = "nomer_d")
	@DatabaseField (id = true)
	private int number;
	
	@ElementList(inline = true, type = Lesson.class)
	@ForeignCollectionField(eager = true)
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
