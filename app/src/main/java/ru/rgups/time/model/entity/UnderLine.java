package ru.rgups.time.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "pod", strict = false)
@DatabaseTable(tableName = "under_line_table")
public class UnderLine {
	
	public static final int WEEK_STATE = 1;

	
	public UnderLine(){
		
	}

	@DatabaseField(foreign = true, columnName = "lesson_id", foreignAutoRefresh = true)
	private Lesson lesson_id;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	
	@Element(name = "predmet", data = false ,required = false)
	@DatabaseField
	private String title;
	
	@Element(name = "tip", data = false ,required = false)
	@DatabaseField
	private String type;
	
	@Element(name = "name_p", data = false ,required = false)
	@DatabaseField
	private String teacher;
	
	@Element(name = "kab", data = false ,required = false)
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
