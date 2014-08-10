package ru.rgups.time.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;

import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.StudentCalendarLessonInfo;

/**
 * Created by timewaistinguru on 09.08.2014.
 */
public class LessonExistingVector extends AsyncTaskLoader<StudentCalendarLessonInfo> {

    private String mTeacher;
    private ContentObserver mObserver = new ForceLoadContentObserver();


    public LessonExistingVector(Context context, String teacher) {
        super(context);
        this.mTeacher = teacher;
        DataManager.getInstance().registerObserver(mObserver);
    }

    @Override
    public StudentCalendarLessonInfo loadInBackground() {
        StudentCalendarLessonInfo result = new StudentCalendarLessonInfo();

        if(mTeacher == null){
            result.setLessonMatrix(LessonManager.getInstance().getStudentLessonMatrix());
            result.setHwVector(LessonManager.getInstance().getHomeWorkVector());
        } else {
            result.setLessonMatrix(LessonManager.getInstance().getTeacherLessonMatrix(mTeacher));
        }

        return result;
    }
}
