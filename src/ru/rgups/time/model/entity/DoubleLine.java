package ru.rgups.time.model.entity;

import org.simpleframework.xml.Root;

import com.j256.ormlite.table.DatabaseTable;

@Root(name = "double")
@DatabaseTable(tableName = "double_line_table")
public class DoubleLine extends LessonInformation{
	
	public DoubleLine(){
	}

	@Override
	public void setShift(int shift) {
		this.setShift(0);
	}
	
	
	
}
