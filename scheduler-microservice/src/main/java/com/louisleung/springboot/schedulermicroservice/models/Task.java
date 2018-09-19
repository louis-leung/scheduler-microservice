package com.louisleung.springboot.schedulermicroservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Document
public class Task {
    public enum TaskStatus {
        AWAITING_ASSIGNMENT,
        ASSIGNED;
    }
    @Id
    private String id;
    private Integer readableId;
    private long duration;
    @Indexed(name="Due Time", direction = IndexDirection.ASCENDING)
    private long dueTimeInMillis;
    private TaskStatus status;


    public void setStatus(TaskStatus status) {
        this.status = status;
    }

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

    public Task(SubmittedTask submittedTask) {
        LocalDateTime ldt = LocalDateTime.of(
                submittedTask.getYear(),
                submittedTask.getMonth(),
                submittedTask.getDay(),
                submittedTask.getHour(),
                submittedTask.getMinute(),
                submittedTask.getSeconds()
        );
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("America/Los_Angeles"));
        this.dueTimeInMillis = zdt.toInstant().toEpochMilli();
        this.duration = submittedTask.getDuration();
        this.readableId = submittedTask.getReadableId();
        this.status = TaskStatus.AWAITING_ASSIGNMENT;
    }


    public Task() {
    }
}
