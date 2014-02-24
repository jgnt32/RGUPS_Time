package ru.rgups.time.model.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@Root(name = "block_1",strict = false)
@DatabaseTable(tableName = "lesson_table")
public class Lesson {
	public static final String TABLE_NAME = "lesson_table";
	public static final String DAY_ID = "day_id";
	public static final String NUMBER = "number";
	public static final String ID = "id";
	
	public Lesson() {
		
	}
	
	private Day day;
	
	private long id;
	

	@Element(name = "para_n")
	private int number;
	
	@Element(name = "vrem")
	private String time;	
	
	@ElementList(inline = true, type = DoubleLine.class,required = false, empty = true)
	private Collection<DoubleLine> doubleLine;
	
	@ElementList(inline = true,required = false, empty = true,type = UnderLine.class )
	private Collection<UnderLine> underLine;
	
	@ElementList(inline = true,required = false, empty = true,type = OverLine.class )
	private Collection<OverLine> overLine;
	
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Collection<DoubleLine> getDoubleLine() {
		return doubleLine;
	}

	public void setDoubleLine(Collection<DoubleLine> doubleLine) {
		this.doubleLine = doubleLine;
	}


	public Collection<UnderLine> getUnderLine() {
		return underLine;
	}

	public void setUnderLine(Collection<UnderLine> underLine) {
		this.underLine = underLine;
	}


	public Collection<OverLine> getOverLine() {
		return overLine;
	}

	public void setOverLine(Collection<OverLine> overLine) {
		this.overLine = overLine;		
	}

	
}
