package ru.rgups.time.model.entity;

import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
@Root(name = "xml")
public class GroupList {
	
	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField
	@Element(name = "nazvanie")
	private String title;
	
	@ForeignCollectionField(eager = false)
	@ElementList(inline=true,type = Group.class)
	private Collection<Group> groupList;

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

	public Collection<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(Collection<Group> groupList) {
		this.groupList = groupList;
	}
}
