package ru.rgups.time.model;

import java.util.ArrayList;
import java.util.Date;

import ru.rgups.time.model.entity.LessonInformation;

public class LessonListElement {
	
	private long id;
	
	private int dayNumber;
	
	private int lessonNumber;
	
	private ArrayList<LessonInformation> information;

    private boolean hasHomeWork;

    private Date date;

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public int getLessonNumber() {
		return lessonNumber;
	}

	public void setLessonNumber(int lessonNumber) {
		this.lessonNumber = lessonNumber;
	}

	public ArrayList<LessonInformation> getInformation() {
		return information;
	}

	public void setInformation(ArrayList<LessonInformation> information) {
		this.information = information;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public boolean isHasHomeWork() {
        return hasHomeWork;
    }

    public void setHasHomeWork(boolean hasHomeWork) {
        this.hasHomeWork = hasHomeWork;
    }

    public String getTitle(){
        StringBuffer buffer = new StringBuffer();
        String result = null;

        if (getInformation() != null) {
            for(LessonInformation inf : getInformation()){
                if(inf != null && inf.getTitle() != null && !buffer.toString().contains(inf.getTitle())){
                    buffer.append(inf.getTitle()).append(", ");
                }
            }
            if(buffer.length() > 0){
                result = buffer.substring(0, buffer.length() - 2);
            }
        }
        return result;

    }

    public String getRooms(){
        String result = null;
        StringBuffer buffer = new StringBuffer();
        if (getInformation() != null) {
            for(LessonInformation inf : getInformation()){
                if(inf != null && inf.getRoom() != null && !buffer.toString().contains(inf.getRoom())){
                    buffer.append(inf.getRoom()).append(", ");
                }
            }
            if(buffer.length() > 0){
                result = buffer.substring(0, buffer.length() - 2);
            }

        }
        return result;
    }

    public String getTypes(){
        StringBuffer buffer = new StringBuffer();
        String result = null;
        if (getInformation() != null) {
            for(LessonInformation inf : getInformation()){
                if(inf != null && inf.getType() != null && !buffer.toString().contains(inf.getType())){
                    buffer.append(inf.getType()).append(", ");
                }
            }
            if(buffer.length() > 0){
                result = buffer.substring(0, buffer.length() - 2);
            }
        }
        return result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(long date) {
        this.date = new Date(date);
    }
}
