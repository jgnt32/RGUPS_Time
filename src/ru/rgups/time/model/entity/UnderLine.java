package ru.rgups.time.model.entity;

import org.simpleframework.xml.Root;

import com.j256.ormlite.table.DatabaseTable;

@Root(name = "pod", strict = false)
@DatabaseTable(tableName = "under_line_table")
public class UnderLine extends LessonInformation{
	
	public UnderLine(){
		
	}

	@Override
	public void setShift(int i) {
		this.setShift(1);
	}
	
	
	
}
