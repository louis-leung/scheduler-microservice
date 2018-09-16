package com.louisleung.springboot.schedulermicroservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Task {

    @Id
    private String id;
    private Integer readableId;
    private long duration;
    private long dueTimeInMillis;

    public Integer getReadableId() {
        return readableId;
    }

    public void setReadableId(Integer readableId) {
        this.readableId = readableId;
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


    public Task(Integer readableId, long duration, long dueTimeInMillis) {
        this.readableId = readableId;
        this.duration = duration;
        this.dueTimeInMillis = dueTimeInMillis;
    }

    public Task() {

    }
}
