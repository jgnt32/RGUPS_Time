package ru.rgups.time.model;

import java.util.ArrayList;

import ru.rgups.time.model.entity.LessonInformation;

public class LessonListElement {
	
	private long id;
	
	private int dayNumber;
	
	private int lessonNumber;
	
	private ArrayList<LessonInformation> information;

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public int getLessonNumber() {
		return lessonNumber;
	}

	public void setLessonNumber(int lessonNumber) {
		this.lessonNumber = lessonNumber;
	}

	public ArrayList<LessonInformation> getInformation() {
		return information;
	}

	public void setInformation(ArrayList<LessonInformation> information) {
		this.information = information;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
}
