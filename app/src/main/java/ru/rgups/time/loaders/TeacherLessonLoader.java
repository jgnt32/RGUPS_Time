package ru.rgups.time.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import ru.rgups.time.datamanagers.LessonManager;

/**
 * Created by timewaistinguru on 09.08.2014.
 */
public class TeacherLessonLoader extends AsyncTaskLoader<Cursor> {

    private int mDayNumber;

    private String mTeacher;

    public TeacherLessonLoader(Context context, int dayOfSemestr, String teacher) {
        super(context);
        mTeacher = teacher;
        mDayNumber = dayOfSemestr;
    }

    @Override
    public Cursor loadInBackground() {
        return LessonManager.getInstance().getTeachersLessonsBySemestrDay(mDayNumber, mTeacher);
    }
}
