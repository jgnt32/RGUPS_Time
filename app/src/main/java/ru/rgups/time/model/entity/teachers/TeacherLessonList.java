package ru.rgups.time.model.entity.teachers;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Collection;

/**
 * Created by jgnt32 on 22.12.2014.
 */
@Root(strict = false)
public class TeacherLessonList {

    @ElementList(inline = true, name = "lessons", type = TeachersLesson.class, required = false, empty = true)
    private Collection<TeachersLesson> lessons;

    public Collection<TeachersLesson> getLessons() {
        return lessons;
    }

    public void setLessons(Collection<TeachersLesson> lessons) {
        this.lessons = lessons;
    }
}
