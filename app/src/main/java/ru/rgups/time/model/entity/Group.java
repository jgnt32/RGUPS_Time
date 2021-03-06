package ru.rgups.time.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Root(name = "blok")
@DatabaseTable(tableName = "group_table")
public class Group {
	
	public static String TABLE_NAME = "group_table";
	public static String GROUP_TITLE = "title";
	public static String ID = "id";
	public static String LEVEL = "level";
	public static String FACULTET_ID = "facultetId";

	@DatabaseField(foreign = true, columnName = "group_id")
	private GroupList list;
	
	@Element
	@DatabaseField(id = true)
	private long id;
	
	@Element(name = "kurs")
	@DatabaseField
	private int level;
	
	@Element(name = "gruppa")
	@DatabaseField
	private String title;
	
	@DatabaseField
	private Long facultetId;

	public GroupList getList() {
		return list;
	}

	public void setList(GroupList list) {
		this.list = list;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getFacultetId() {
		return facultetId;
	}

	public void setFacultetId(Long facultetId) {
		this.facultetId = facultetId;
	}
	
	
	
}
