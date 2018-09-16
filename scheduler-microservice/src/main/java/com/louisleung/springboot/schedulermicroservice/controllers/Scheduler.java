package com.louisleung.springboot.schedulermicroservice.controllers;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskConsumerRepository;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {
    private static TaskRepository taskRepository;
    private static TaskConsumerRepository taskConsumerRepository;

    public Scheduler(TaskRepository taskRepository, TaskConsumerRepository taskConsumerRepository) {
        this.taskRepository = taskRepository;
        this.taskConsumerRepository = taskConsumerRepository;
    }

    public static List<Task> readFromTaskRepo() {
        return taskRepository.findAll();
    }
}
