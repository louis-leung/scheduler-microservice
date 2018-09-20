package com.louisleung.springboot.schedulermicroservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class TaskConsumer {

    @Id
    private String id;
    private List<Task> assignedTasks;

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
        this.id = id;
    }

    /* Default constructor necessary to map Response Bodies to POJOS. */
    public TaskConsumer() {

    }
}
