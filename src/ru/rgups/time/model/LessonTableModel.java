package ru.rgups.time.model;

import java.util.ArrayList;

import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.utils.PreferenceManager;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lesson_table")
public class LessonTableModel {
	
	public LessonTableModel() {
		this.setId(PreferenceManager.getInstance().getGroupId());
	}
	
	public static final String TABLE_NAME = "lesson_table";
	public static final String ID = "id";
	public static final String DAY = "day";
	public static final String NUMBER = "number";
	public static final String GROUP_ID = "groupId";
	public static final String WEEK_STATE = "weekState";

	
	@DatabaseField(id = true)
	private long id;
	
	@DatabaseField
	private int day;
	
	@DatabaseField
	private int number;
	
	@DatabaseField
	private long groupId;
	
	@DatabaseField
	private int weekState;
	
	private ArrayList<LessonInformation> information;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public int getWeekState() {
		return weekState;
	}

	public void setWeekState(int weekState) {
		this.weekState = weekState;
	}

	
}
