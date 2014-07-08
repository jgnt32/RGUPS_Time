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
public class FacultetList {
	
	@DatabaseField(id = true)
	private int id;
	
	public FacultetList(){
		
	}
	
	@DatabaseField
	@Element(name = "nazvanie")
	private String title;
	
	@ForeignCollectionField(eager = false)
	@ElementList(inline=true,type = Facultet.class)
	private Collection<Facultet> facultetList;

	public Collection<Facultet> getFacultetList() {
		return facultetList;
	}

	public void setFacultetList(Collection<Facultet> facultetList) {
		this.facultetList = facultetList;
	}
	
	
}
