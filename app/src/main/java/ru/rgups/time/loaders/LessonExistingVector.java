package ru.rgups.time.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import ru.rgups.time.datamanagers.LessonManager;

/**
 * Created by timewaistinguru on 09.08.2014.
 */
public class LessonExistingVector extends AsyncTaskLoader<boolean[][]> {

    private String mTeacher;

    public LessonExistingVector(Context context, String teacher) {
        super(context);
        this.mTeacher = teacher;
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
