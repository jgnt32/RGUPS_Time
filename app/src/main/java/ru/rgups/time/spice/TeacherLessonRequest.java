package ru.rgups.time.spice;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import ru.rgups.time.model.TeacherManager;
import ru.rgups.time.model.entity.teachers.TeacherLessonList;

/**
 * Created by jgnt32 on 23.12.2014.
 */
public class TeacherLessonRequest extends SpringAndroidSpiceRequest<TeacherLessonList> {

    private String mUrl = "http://rgups.ru/time/xml/teachers.php?id=%id&year=2014&semester=1";
    private Context context;
    private long mTeacherId;

    public TeacherLessonRequest(Context context, long teacherId) {
        super(TeacherLessonList.class);
        this.context = context;
        mTeacherId = teacherId;
    }

    @Override
    public TeacherLessonList loadDataFromNetwork() throws Exception {

        TeacherLessonList result = getRestTemplate().getForObject( mUrl.replace("%id", Long.toString(mTeacherId)), TeacherLessonList.class);
        TeacherManager.getInstance(context).saveTeacherLesson(result.getLessons(), mTeacherId);
        return result;
    }
}
