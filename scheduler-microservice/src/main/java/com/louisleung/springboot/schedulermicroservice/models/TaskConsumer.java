package com.louisleung.springboot.schedulermicroservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Document
public class TaskConsumer {

    @Id
    private String id;
    private List<Task> assignedTasks;
    private long epochTimeWhenAvailable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public TaskConsumer(String id) {
        this.epochTimeWhenAvailable = Instant.now().toEpochMilli();
        this.id = id;
    }

    /* Default constructor necessary to map Response Bodies to POJOS. */
    public TaskConsumer() {
        this.epochTimeWhenAvailable = Instant.now().toEpochMilli();
    }
}
