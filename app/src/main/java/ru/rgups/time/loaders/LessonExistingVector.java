package ru.rgups.time.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;

import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;

/**
 * Created by timewaistinguru on 09.08.2014.
 */
public class LessonExistingVector extends AsyncTaskLoader<boolean[][]> {

    private String mTeacher;
    private ContentObserver mObserver = new ForceLoadContentObserver();


    public LessonExistingVector(Context context, String teacher) {
        super(context);
        this.mTeacher = teacher;
        DataManager.getInstance().registerObserver(mObserver);
    }

    @Override
    public boolean[][] loadInBackground() {
        if(mTeacher == null){
            return LessonManager.getInstance().getStudentLessonMatrix();
        } else {
            return LessonManager.getInstance().getTeacherLessonMatrix(mTeacher);
        }
    }
}
