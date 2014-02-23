package ru.rgups.time.model.entity;

import org.simpleframework.xml.Element;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lesson_information_table")
public class LessonInformation {
	
	public static final String TABLE_NAME = "lesson_information_table";
	public static final String LESSON_TITLE = "title";
	public static final String LESSON_TYPE = "type";
	public static final String TEACHER_NAME = "teacher";
	public static final String ROOM = "room";
	public static final String ID = "id";
	public static final String LESSON_ID = "lessonId";
	public static final String GROUP_ID = "groupId";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private long lessonId;
	
	@DatabaseField
	private long groupId;

	@Element(name = "predmet")
	@DatabaseField
	private String title;
	
	@Element(name = "tip")
	@DatabaseField
	private String type;
	
	@Element(name = "name_p")
	@DatabaseField
	private String teacher;
	
	@Element(name = "kab")
	@DatabaseField
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

	public long getLessonId() {
		return lessonId;
	}

	public void setLessonId(long lessonId) {
		this.lessonId = lessonId;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
}
