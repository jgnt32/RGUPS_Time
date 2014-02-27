package ru.rgups.time.model;

import java.util.ArrayList;
import java.util.Date;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "home_work_table")
public class HomeWork {
	public static String TABLE_NAME = "home_work_table";
	public static String ID = "id";
	public static String DATE = "date";
	public static String LESSON_ID = "lessonId";
	public static String MESSAGE = "message";
	public static String COMPLITE = "complite";
	public static String IMAGES = "images";


	private long id;
	
	private Date date;
	
	private long lessonId;
	
	private String message;
	
	private boolean complite;
	
	private ArrayList<String> images;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getLessonId() {
		return lessonId;
	}

	public void setLessonId(long lessonId) {
		this.lessonId = lessonId;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<String> getImages() {
		return images;
	}

	public void setImages(ArrayList<String> images) {
		this.images = images;
	}

	public boolean isComplite() {
		return complite;
	}

	public void setComplite(boolean complite) {
		this.complite = complite;
	}
	
	

}
