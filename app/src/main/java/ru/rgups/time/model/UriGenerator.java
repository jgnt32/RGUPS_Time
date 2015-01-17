package ru.rgups.time.model;

import android.net.Uri;

import ru.rgups.time.model.entity.teachers.Teacher;
import ru.rgups.time.model.entity.teachers.TeachersLesson;

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
        } else if (clazz == TeachersLesson.class){
            result = Uri.parse(SCHEME + "tachers_lesson" + "/" + rowId);
        }

        return result;

    }

}
