package ru.rgups.time.model.entity.teachers;

import org.simpleframework.xml.Element;

import io.realm.RealmObject;

/**
 * Created by jgnt32 on 22.12.2014.
 */

@Element(name = "lesson")
public class TeachersLesson extends RealmObject{

    @Element(name = "day_week")
    private String dayOfWeek;

    @Element(name = "number")
    private int number;

    @Element(name = "time_from")
    private String timeFrom;

    @Element(name = "time_to")
    private String timeTo;

    @Element(name = "periodicity")
    private String periodicity;

    @Element(name = "subject")
    private String subject;

    @Element(name = "kind")
    private String kind;

    @Element(name = "teacher_id")
    private long teacherId;

    @Element(name = "teacher_name")
    private long teacherName;

    @Element(name = "room")
    private String room;

    @Element(name = "study_group")
    private String studyGroup;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(long teacherName) {
        this.teacherName = teacherName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(String studyGroup) {
        this.studyGroup = studyGroup;
    }



}
