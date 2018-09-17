package com.louisleung.springboot.schedulermicroservice.controllers;


import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.SubmittedTask;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/task")
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public void addTask(@RequestBody SubmittedTask submittedTask) {
        taskRepository.save(new Task(submittedTask));
    }
}
