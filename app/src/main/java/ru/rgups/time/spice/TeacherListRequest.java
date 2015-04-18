package ru.rgups.time.spice;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import ru.rgups.time.model.TeacherManager;
import ru.rgups.time.model.entity.teachers.TeacherList;
import ru.rgups.time.rest.UrlBuilder;
import ru.rgups.time.rest.UrlConstants;

/**
 * Created by jgnt32 on 22.12.2014.
 */
public class TeacherListRequest extends SpringAndroidSpiceRequest<TeacherList> {

    private String url = null;

    public TeacherListRequest() {
        super(TeacherList.class);
        url = UrlBuilder.make(UrlConstants.TEACHERS, null);
    }

    @Override
    public TeacherList loadDataFromNetwork() throws Exception {
        TeacherList result = getRestTemplate().getForObject( url, TeacherList.class);
        TeacherManager.getInstance().saveTeachers(result.getTeachers());
        return result;
    }
}
