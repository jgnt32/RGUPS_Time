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
	
	public Lesson() {
	}
	
	@DatabaseField(foreign = true, columnName = "day_id")
	private Day day;
	
	@DatabaseField(id = true)
	private int id;
	
	@Element(name = "para_n")
	@DatabaseField
	private int number;
	
	@Element(name = "vrem")
	@DatabaseField
	private String time;
	
	@ForeignCollectionField(eager = true)
	private Collection<LessonInformation> infromation = new ArrayList<LessonInformation>();
	
	
	@ElementList(inline = true, type = DoubleLine.class,required = false, empty = true)
	@ForeignCollectionField(eager = true)
	private Collection<DoubleLine> doubleLine;
	
	@ElementList(inline = true,required = false, empty = true,type = UnderLine.class )
	@ForeignCollectionField(eager = true)	
	private Collection<UnderLine> underLine;
	
	@ElementList(inline = true,required = false, empty = true,type = OverLine.class )
	@ForeignCollectionField(eager = true)
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

	public Collection<LessonInformation> getInfromation() {
		return infromation;
	}

	public void setInfromation(Collection<LessonInformation> infromation) {
		
		this.infromation = infromation;
	}

	public Collection<DoubleLine> getDoubleLine() {
		return doubleLine;
	}

	public void setDoubleLine(Collection<DoubleLine> doubleLine) {
//		infromation.addAll(doubleLine);
/*		for(DoubleLine doubl:doubleLine){
//			doubl.setDayNumber(dayNumber);
			doubl.setLessonNumber(number);
			doubl.setTime(time);
		}*/
		this.doubleLine = doubleLine;
	}


	public Collection<UnderLine> getUnderLine() {
		return underLine;
	}

	public void setUnderLine(Collection<UnderLine> underLine) {
	//	infromation.addAll(underLine);
/*		for(UnderLine under:underLine){
//			under.setDayNumber(dayNumber);
			under.setLessonNumber(number);
			under.setTime(time);
		}*/
		this.underLine = underLine;
	}


	public Collection<OverLine> getOverLine() {
		return overLine;
	}

	public void setOverLine(Collection<OverLine> overLine) {
	//	infromation.addAll(overLine);
	/*	for(OverLine over:overLine){
		//	Log.w("try set day namber",""+dayNumber);
		//	over.setDayNumber(dayNumber);
			over.setLessonNumber(number);
			over.setTime(time);
		}*/
		this.overLine = overLine;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	

}
