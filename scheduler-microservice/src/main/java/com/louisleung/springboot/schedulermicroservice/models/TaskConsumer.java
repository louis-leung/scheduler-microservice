package com.louisleung.springboot.schedulermicroservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class TaskConsumer {

    @Id
    private String id;
    private Integer readableId;
    private List<Task> assignedTasks;

    public Integer getReadableId() {
        return readableId;
    }

    public void setReadableId(Integer readableId) {
        this.readableId = readableId;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public TaskConsumer(Integer readableId) {
        this.readableId = readableId;
    }

    /* Default constructor necessary to map Response Bodies to POJOS. */
    public TaskConsumer() {

    }
}
