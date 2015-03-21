package ru.rgups.time.model;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.rgups.time.RTApplication;
import ru.rgups.time.model.entity.teachers.Teacher;
import ru.rgups.time.model.entity.teachers.TeachersLesson;
import ru.rgups.time.utils.CalendarManager;

/**
 * Created by jgnt32 on 22.12.2014.
 */
public class TeacherManager {

    private Context context;

    private static TeacherManager mInstance;

    private final String[] DAYS_OF_WEEK = new String[]{"Понедельник","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"};

    private final String[] WEEK_STATE = new String[]{"еженедельно","под чертой","над чертой"};

    public TeacherManager(Context context) {
        this.context = context;
    }

    public static TeacherManager getInstance() {
        if (mInstance == null) {
            mInstance = new TeacherManager(RTApplication.getContext());
        }
        return mInstance;
    }


    public void saveTeachers(Collection<Teacher> teachers) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        if (!teachers.isEmpty()) {
            realm.clear(Teacher.class);
        }
        realm.commitTransaction();

        realm.beginTransaction();
        for (Teacher teacher : teachers) {
            Teacher local = realm.createObject(Teacher.class);
            local.setId(teacher.getId());
            local.setFullName(teacher.getFullName());
            local.setShortName(teacher.getFullName());
        }

        realm.commitTransaction();
        realm.close();
        context.getContentResolver().notifyChange(UriGenerator.generate(Teacher.class, null), null);
    }


    public List<Teacher> getTeachers(String name){
        List<Teacher> result = new ArrayList<>();
        Realm realm = Realm.getInstance(context);
        RealmResults<Teacher> teachers;

        if (name == null || name.isEmpty()) {
            teachers = realm.where(Teacher.class).findAll();
        } else {
            teachers = realm.where(Teacher.class).contains("fullName", name, true).findAll();
        }

        teachers.sort("shortName");

        for (Teacher teacher : teachers) {
            result.add(Teacher.clone(teacher));
        }
        realm.close();
        return result;
    }

    public RealmResults<TeachersLesson> getTeachersLessons(long teacherId, int dayOfSemester){
        Realm realm = Realm.getInstance(context);

        int weekState = CalendarManager.getWeekState(dayOfSemester);
        int dayOfWeek = CalendarManager.getDayOfWeek(dayOfSemester);


        RealmResults<TeachersLesson> result = realm
                .where(TeachersLesson.class)
                .equalTo("teacherId", teacherId)
                .equalTo("dayOfWeek", DAYS_OF_WEEK[dayOfWeek])
          /*      .beginGroup()
                    .equalTo("periodicity", WEEK_STATE[weekState])
                    .or()
                    .equalTo("periodicity", WEEK_STATE[0])
                    .endGroup()*/
                .findAll();

        return result;
    }

    public void saveTeacherLesson(Collection<TeachersLesson> lessons, long teacherId){
        Realm realm = Realm.getInstance(context);

        TeachersLesson newLesson = null;

        List<TeachersLesson> lessonsToSave = new ArrayList<>();

        for (TeachersLesson lesson : lessons) {
            if (newLesson == null) {
                newLesson = lesson;
                newLesson.setStudyGroups(stringToArrayJson(lesson.getStudyGroup()));

            } else if (newLesson.getDayOfWeek().equalsIgnoreCase(lesson.getDayOfWeek()) && newLesson.getNumber() == lesson.getNumber() && newLesson.getPeriodicity().equalsIgnoreCase(lesson.getPeriodicity())) {
                addStadyGroup(newLesson, lesson.getStudyGroup());
            } else {
                lessonsToSave.add(newLesson);
                newLesson = lesson;
                newLesson.setStudyGroups(stringToArrayJson(lesson.getStudyGroup()));
            }

        }

        lessonsToSave.add(newLesson);

        realm.beginTransaction();
        if (!lessons.isEmpty()) {
            RealmResults<TeachersLesson> realmResult = realm
                    .where(TeachersLesson.class)
                    .equalTo("teacherId", teacherId)
                    .findAll();
            realmResult.clear();
        }
        realm.commitTransaction();

        realm.beginTransaction();
        for (TeachersLesson lesson : lessonsToSave) {
            TeachersLesson toDbLesson = realm.createObject(TeachersLesson.class);
            cloneLesson(toDbLesson, lesson);
        }
        realm.commitTransaction();

        realm.close();
        context.getContentResolver().notifyChange(UriGenerator.generate(TeachersLesson.class, null), null);

    }

    public void cloneLesson(TeachersLesson newLesson, TeachersLesson oldLesson){


        newLesson.setDayOfWeek(oldLesson.getDayOfWeek());
        newLesson.setNumber(oldLesson.getNumber());
        newLesson.setTimeFrom(oldLesson.getTimeFrom());
        newLesson.setTimeTo(oldLesson.getTimeTo());
        newLesson.setPeriodicity(oldLesson.getPeriodicity());
        newLesson.setSubject(oldLesson.getSubject());
        newLesson.setKind(oldLesson.getKind());
        newLesson.setTeacherId(oldLesson.getTeacherId());
        newLesson.setTeacherName(oldLesson.getTeacherName());
        newLesson.setRoom(oldLesson.getRoom());
        newLesson.setStudyGroup(oldLesson.getStudyGroup());
        newLesson.setStudyGroups(oldLesson.getStudyGroups());
    }


    public ArrayList<String> getGroups(TeachersLesson lesson){
        ArrayList<String> result = null;

        Gson gson = new Gson();
        result = gson.fromJson(lesson.getStudyGroups(), ArrayList.class);

        return result;
    }


    public void addStadyGroup(TeachersLesson lesson, String group){
        ArrayList<String> result = null;

        Gson gson = new Gson();
        result = gson.fromJson(lesson.getStudyGroups(), ArrayList.class);
        if (result == null) {
            result = new ArrayList<>();
        }
        result.add(group);
        lesson.setStudyGroups(gson.toJson(result));
    }

    public String stringToArrayJson(String group){
        String result = null;

        ArrayList<String> strings = new ArrayList<>();
        strings.add(group);

        Gson gson = new Gson();

        result = gson.toJson(strings);

        return result;
    }

}