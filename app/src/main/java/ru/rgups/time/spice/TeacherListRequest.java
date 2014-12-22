package ru.rgups.time.spice;

import android.content.Context;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import ru.rgups.time.model.TeacherManager;
import ru.rgups.time.model.entity.teachers.TeacherList;

/**
 * Created by jgnt32 on 22.12.2014.
 */
public class TeacherListRequest extends SpringAndroidSpiceRequest<TeacherList> {

    private String url = "http://rgups.ru/time/xml/teachers.php";
    private Context context;

    public TeacherListRequest(Context context) {
        super(TeacherList.class);
        this.context = context;
    }

    @Override
    public TeacherList loadDataFromNetwork() throws Exception {
        TeacherList result = getRestTemplate().getForObject( url, TeacherList.class);
        TeacherManager.getInstance(context).saveTeachers(result.getTeachers());
        return result;
    }
}
