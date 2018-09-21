package com.louisleung.springboot.schedulermicroservice.models;

import java.util.List;

/* This represents an assignment of tasks and projected time of finish to any given consumer. */
public class Assignment {
    private List<Task> tasks;
    private long projectedTime;

    public Assignment(List<Task> tasks, long projectedTime) {
        this.tasks = tasks;
        this.projectedTime = projectedTime;
    }

    public List<Task> retrieveTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public long getProjectedTime() {
        return projectedTime;
    }

    public void setProjectedTime(long projectedTime) {
        this.projectedTime = projectedTime;
    }
}
