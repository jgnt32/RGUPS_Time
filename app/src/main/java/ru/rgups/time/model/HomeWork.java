package ru.rgups.time.model;

import java.util.ArrayList;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "home_work_table")
public class HomeWork {
	public static final String TABLE_NAME = "home_work_table";
	public static final String ID = "id";
	public static final String DATE = "date";
	public static final String LESSON_ID = "lessonId";
	public static final String MESSAGE = "message";
	public static final String COMPLITE = "complite";
	public static final String IMAGES = "images";
	public static final String GROUP_ID = "groupId";
	
	@DatabaseField(generatedId = true)
	private long id;
	
	@DatabaseField
	private Date date;
	
	@DatabaseField
	private long groupId;

	@DatabaseField
	private long lessonId;
	
	@DatabaseField
	private String message;
	
	@DatabaseField
	private boolean complite;
	
	private String lessonTitle;
	
	@DatabaseField(dataType = DataType.SERIALIZABLE)
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

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getLessonTitle() {
		return lessonTitle;
	}

	public void setLessonTitle(String lessonTitle) {
		this.lessonTitle = lessonTitle;
	}

}
