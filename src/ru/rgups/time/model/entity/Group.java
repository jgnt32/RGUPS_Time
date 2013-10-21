package ru.rgups.time.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Root(name = "blok")
@DatabaseTable(tableName = "group_table")
public class Group {

	@DatabaseField(foreign = true, columnName = "group_id")
	private GroupList list;
	
	@Element
	@DatabaseField(id = true)
	private int id;
	
	@Element(name = "kurs")
	@DatabaseField
	private int level;
	
	@Element(name = "gruppa")
	@DatabaseField
	private String title;

	public GroupList getList() {
		return list;
	}

	public void setList(GroupList list) {
		this.list = list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	
}
