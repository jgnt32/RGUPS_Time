package ru.rgups.time.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import ru.rgups.time.model.TeacherManager;
import ru.rgups.time.model.UriGenerator;
import ru.rgups.time.model.entity.teachers.Teacher;

/**
 * Created by timewaistinguru on 10.08.2014.
 */
public class TecherListLoader extends AsyncTaskLoader<List<Teacher>> {

    private ContentObserver contentObserver = new ForceLoadContentObserver();
    private String mName;


    public TecherListLoader(Context context, String name) {
        super(context);
        context.getContentResolver().registerContentObserver(UriGenerator.generate(Teacher.class, null), true, contentObserver);
        mName = name;
    }

    @Override
    public List<Teacher> loadInBackground() {
        return TeacherManager.getInstance().getTeachers(mName);
    }
}
