package ru.rgups.time.model.entity;

import org.simpleframework.xml.Root;

import com.j256.ormlite.table.DatabaseTable;
@Root(name = "nad", strict = false)
@DatabaseTable(tableName = "over_line_table")
public class OverLine extends LessonInformation{

	public OverLine(){
		
	}

	@Override
	public void setShift(int shift) {
		this.setShift(2);
	}
	
	
	
	
	
}
