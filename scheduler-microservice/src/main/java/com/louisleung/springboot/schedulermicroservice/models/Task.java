package com.louisleung.springboot.schedulermicroservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

@Document
public class Task {

    @Id
    private String id;
    private long duration;
    private TaskStatus status;
    private Date dueTimeInUTC;
    @Indexed(name="Due Time", direction = IndexDirection.ASCENDING)
    private long dueTimeInMillis;

    public Date getDueTimeInUTC() {
        return dueTimeInUTC;
    }

    public void setDueTimeInUTC(Date dueTimeInUTC) {
        this.dueTimeInUTC = dueTimeInUTC;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() { return this.status; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDueTimeInMillis() {
        return dueTimeInMillis;
    }

    public void setDueTimeInMillis(long dueTimeInMillis) {
        this.dueTimeInMillis = dueTimeInMillis;
    }

    public Task(Date dateAndtime, long duration) {
        /* Assumes all task times are submitted in UTC time. Due time date/millis is by UTC. */
        this.dueTimeInUTC = dateAndtime;
//        this.dueTimeInMillis = dateAndtime.toEpochSecond(ZoneOffset.UTC);
        this.dueTimeInMillis = dateAndtime.getTime();
        this.duration = duration;
        this.status = TaskStatus.AWAITING_ASSIGNMENT;
    }


    public Task() {
    }
}
