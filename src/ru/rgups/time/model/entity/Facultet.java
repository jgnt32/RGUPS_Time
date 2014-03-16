package ru.rgups.time.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Root(name = "blok")
@DatabaseTable(tableName = "facultet_table")
public class Facultet {
	public static final String TABLE_NAME = "facultet_table";
	public static final String TITLE = "name";
	public static final String ID = "id";
	
	@DatabaseField(foreign = true, columnName = "list_id")
	private FacultetList list;
	
	@Element(name = "fak")
	@DatabaseField
	private String name;
	
	@Element
	@DatabaseField(id = true)
	private int id;
	
	public Facultet(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public class ValidFacultet extends Facultet {

		public int getId() {
			return id;
		}
	}

	@Root
	public class InvalidFacultet extends Facultet {

		public InvalidFacultet(){
			
		}
		
		public int getId() {
			return id;
		}
	}

	@Root(name="blok")
	public class FixedFacultet extends Facultet {
	}


}
