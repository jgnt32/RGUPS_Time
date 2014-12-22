package ru.rgups.time.model;

import android.net.Uri;

import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.teachers.Teacher;

/**
 * Created by jgnt32 on 22.12.2014.
 */
public class UriGenerator {

    private static final String SCHEME = "content://";

    public static Uri generate (Class<?> clazz, Long id) {

        String rowId = id != null ? id.toString(): null;
        Uri result = null;
        if (clazz == Teacher.class) {
            result = Uri.parse(SCHEME + "teacher" + "/" + rowId);
        } else if (clazz == Lesson.class){
            result = Uri.parse(SCHEME + "lesson" + "/" + rowId);
        }

        return result;

    }

}
