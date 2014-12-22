package ru.rgups.time.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.rgups.time.model.entity.teachers.Teacher;

/**
 * Created by jgnt32 on 22.12.2014.
 */
public class TeacherManager {

    private Context context;

    private static TeacherManager mInstance;

    public TeacherManager(Context context) {
        this.context = context;
    }

    public static TeacherManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TeacherManager(context);
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

}