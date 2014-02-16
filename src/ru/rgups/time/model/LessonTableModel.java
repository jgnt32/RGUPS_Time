package ru.rgups.time.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lesson_table")
public class LessonTableModel {
	
	public static final String TABLE_NAME = "lesson_table";
	public static final String ID = "id";
	public static final String DAY = "day";
	public static final String NUMBER = "number";
	public static final String GROUP_ID = "groupId";
	public static final String WEEK_STATE = "weekState";
	public static final String LESSON_TITLE = "lessonTitle";
	public static final String LESSON_TYPE = "lessonType";
	public static final String TEACHER_NAME = "teacherName";
	public static final String ROOM = "room";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private int day;
	
	@DatabaseField
	private int number;
	
	@DatabaseField
	private int groupId;
	
	@DatabaseField
	private int weekState;
	
	@DatabaseField
	private String lessonTitle;
	
	@DatabaseField
	private String lessonType;
	
	@DatabaseField
	private String teacherName;
	
	@DatabaseField
	private String room;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getWeekState() {
		return weekState;
	}

	public void setWeekState(int weekState) {
		this.weekState = weekState;
	}

	public String getLessonTitle() {
		return lessonTitle;
	}

	public void setLessonTitle(String lessonTitle) {
		this.lessonTitle = lessonTitle;
	}

	public String getLessonType() {
		return lessonType;
	}

	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	

}
