package ru.rgups.time.model.entity;

import java.util.Collection;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "block_1",strict = false)
public class Lesson {
	
	@Element(name = "para_n")
	private int number;
	
	@Element(name = "vrem")
	private String time;
	
	
	private Collection<LessonInformation> infromation;
	
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
		this.doubleLine = doubleLine;
	//	infromation.addAll(arg0)
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
//		infromation.addAll(overLine);
	}


	
	

}
