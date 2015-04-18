package ru.rgups.time.model.entity.teachers;

import org.simpleframework.xml.Element;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;

/**
 * Created by jgnt32 on 22.12.2014.
 */

public class Teacher extends RealmObject {


    @Ignore public static final String ID = "id";
    @Ignore public static final String NAME_SHORT = "shortName";
    @Ignore public static final String NAME_FULL = "fullName";

    @Element(name = "id")
    private long id;

    @Element(name = "short_name")
    @Index
    private String shortName;

    @Element(name = "full_name")
    private String fullName;



    public static Teacher clone(Teacher teacher) {
        Teacher result = new Teacher();
        result.setFullName(teacher.getFullName());
        result.setId(teacher.getId());
        result.setShortName(teacher.getShortName());
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
