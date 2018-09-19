package com.louisleung.springboot.schedulermicroservice.controllers;


import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.SubmittedTask;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    /*
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    */

    @PostMapping
    public void addTask(@RequestBody SubmittedTask submittedTask) {
        taskRepository.save(new Task(submittedTask));
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }
}
