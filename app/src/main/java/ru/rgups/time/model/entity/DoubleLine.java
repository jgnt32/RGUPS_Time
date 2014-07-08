package ru.rgups.time.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Root(name = "double")
@DatabaseTable(tableName = "double_line_table")
public class DoubleLine {
	
	public static final int WEEK_STATE = 2;
	
	public DoubleLine(){
	}

	@DatabaseField(foreign = true, columnName = "lesson_id", foreignAutoRefresh = true)
	private Lesson lesson_id;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	
	@Element(name = "predmet",  required = false, data = false)
	@DatabaseField
	private String title;
	
	@Element( name = "tip", data = false ,required = false)
	@DatabaseField
	private String type;
	
	@Element(required = false, name = "name_p", data = false)
	@DatabaseField
	private String teacher;
	
	@Element(required = false, name = "kab", data = false)
	@DatabaseField
	private String room;

	public Lesson getLesson_id() {
		return lesson_id;
	}

	public void setLesson_id(Lesson lesson_id) {
		this.lesson_id = lesson_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
