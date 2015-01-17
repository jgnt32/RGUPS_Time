package ru.rgups.time.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;

import io.realm.RealmResults;
import ru.rgups.time.model.UriGenerator;
import ru.rgups.time.model.entity.teachers.TeachersLesson;

/**
 * Created by timewaistinguru on 09.08.2014.
 */
public class TeacherLessonLoader extends AsyncTaskLoader<RealmResults<TeachersLesson>> {

    private int mDayNumber;
    private ContentObserver mObserver = new ForceLoadContentObserver();

    private long mTeacherId;

    public TeacherLessonLoader(Context context, int dayOfSemestr, long teacherId) {
        super(context);
        mTeacherId = teacherId;
        mDayNumber = dayOfSemestr;
        context.getContentResolver().registerContentObserver(UriGenerator.generate(TeachersLesson.class, teacherId), true, mObserver);
    }

    @Override
    public RealmResults<TeachersLesson> loadInBackground() {
        return null;
    }
}
