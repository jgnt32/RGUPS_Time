package ru.rgups.time.model.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by timewaistinguru on 13.07.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerHomeWorkEntity {

    private long lessonId;

    private String message;

    private long groupId;

    private Date date;



}
