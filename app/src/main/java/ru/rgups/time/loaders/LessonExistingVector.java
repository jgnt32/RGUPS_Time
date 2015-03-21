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



    private long mTeacherId = -1;
    private ContentObserver mObserver = new ForceLoadContentObserver();

    public LessonExistingVector(Context context){
        this(context, -1);

    }


    public LessonExistingVector(Context context, long teacherId) {
        super(context);
        this.mTeacherId = teacherId;
        DataManager.getInstance().registerObserver(mObserver);
    }

    @Override
    public StudentCalendarLessonInfo loadInBackground() {
        StudentCalendarLessonInfo result = new StudentCalendarLessonInfo();

        if(mTeacherId == -1){
            result.setLessonMatrix(LessonManager.getInstance().getStudentLessonMatrix());
            result.setHwVector(LessonManager.getInstance().getHomeWorkVector());
        } else {
            result.setLessonMatrix(LessonManager.getInstance().getTeacherLessonMatrix(mTeacherId));
        }

        return result;
    }
}
