package ru.rgups.time.model.entity.teachers;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Collection;

/**
 * Created by jgnt32 on 22.12.2014.
 */

@Root(strict = false)
public class TeacherList {

    @ElementList(inline = true, type = Teacher.class, required = false, empty = true)
    private Collection<Teacher> teachers;

    public Collection<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<Teacher> teachers) {
        this.teachers = teachers;
    }
}
