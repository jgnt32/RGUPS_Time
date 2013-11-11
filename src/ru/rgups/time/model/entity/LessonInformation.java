package ru.rgups.time.model.entity;

import org.simpleframework.xml.Element;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lesson_information_table")
public class LessonInformation {
	
	@DatabaseField(foreign = true, columnName = "lesson_id")
	private Lesson lesson_id;
	
	@DatabaseField(id = true)
	private int id;
	
	private int shift;	
	
	private int dayNumber;
	
	private int lessonNumber;

	private String time;
	
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

	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public Lesson getLesson_id() {
		return lesson_id;
	}

	public void setLesson_id(Lesson lesson_id) {
		this.lesson_id = lesson_id;
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public int getLessonNumber() {
		return lessonNumber;
	}

	public void setLessonNumber(int number) {
		this.lessonNumber = number;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
