package com.louisleung.springboot.schedulermicroservice.models;

import com.louisleung.springboot.schedulermicroservice.exceptions.ExpiredTaskException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document
public class Task {

    @Id
    private String id;
    private long duration;
    private TaskStatus status;
    @Indexed(name="Due Time", direction = IndexDirection.ASCENDING)
    private long dueTimeInMillis;
    @Indexed(name="Latest Start Time", direction = IndexDirection.ASCENDING)
    private long latestStartTimeInMillis;

    public long getLatestStartTimeInMillis() {
        return latestStartTimeInMillis;
    }

    public void setLatestStartTimeInMillis(long latestStartTimeInMillis) {
        this.latestStartTimeInMillis = latestStartTimeInMillis;
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

    public Task(Date dateAndtime, long duration) throws ExpiredTaskException {
        this.dueTimeInMillis = dateAndtime.getTime();
        this.duration = duration;
        this.status = TaskStatus.AWAITING_ASSIGNMENT;
        this.latestStartTimeInMillis = dueTimeInMillis - duration;
        if (latestStartTimeInMillis <= System.currentTimeMillis()) {
            throw new ExpiredTaskException();
        }
    }


    public Task() {
    }
}
