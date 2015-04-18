package ru.rgups.time.model.entity.teachers;

import org.simpleframework.xml.Element;

import io.realm.RealmObject;

/**
 * Created by jgnt32 on 22.12.2014.
 */

public class TeachersLesson extends RealmObject{

    @Element(name = "day_week", required = false)
    private String dayOfWeek;

    @Element(name = "number", required = false)
    private int number;

    @Element(name = "time_from", required = false)
    private String timeFrom;

    @Element(name = "time_to", required = false)
    private String timeTo;

    @Element(name = "periodicity", required = false)
    private String periodicity;

    @Element(name = "subject", required = false)
    private String subject;

    @Element(name = "kind", required = false)
    private String kind;

    @Element(name = "teacher_id", required = false)
    private long teacherId;

    @Element(name = "teacher_name", required = false)
    private String teacherName;

    @Element(name = "room", required = false)
    private String room;

    @Element(name = "study_group", required = false)
    private String studyGroup;

    private String studyGroups;

    public String getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(String studyGroups) {
        this.studyGroups = studyGroups;
    }

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
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
