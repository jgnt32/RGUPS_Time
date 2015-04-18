package ru.rgups.time.spice;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import java.util.HashMap;
import java.util.Map;

import ru.rgups.time.model.TeacherManager;
import ru.rgups.time.model.entity.teachers.TeacherLessonList;
import ru.rgups.time.rest.UrlBuilder;
import ru.rgups.time.rest.UrlConstants;
import ru.rgups.time.utils.CalendarManager;

/**
 * Created by jgnt32 on 23.12.2014.
 */
public class TeacherLessonRequest extends SpringAndroidSpiceRequest<TeacherLessonList> {

    private String url = null;
    private long mTeacherId;

    public TeacherLessonRequest(long teacherId) {
        super(TeacherLessonList.class);
        mTeacherId = teacherId;

        Map<String, String> params = new HashMap<>();
        params.put("id", Long.toString(mTeacherId));
        params.put("year", Integer.toString(CalendarManager.getYear()));
        params.put("semester", Integer.toString(CalendarManager.getSemester()));

        url = UrlBuilder.make(UrlConstants.TEACHERS, params);
    }

    @Override
    public TeacherLessonList loadDataFromNetwork() throws Exception {

        TeacherLessonList result = getRestTemplate().getForObject(url, TeacherLessonList.class);
        TeacherManager.getInstance().saveTeacherLesson(result.getLessons(), mTeacherId);
        return result;
    }
}
