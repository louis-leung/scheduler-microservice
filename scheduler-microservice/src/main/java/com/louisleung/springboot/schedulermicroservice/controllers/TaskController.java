package com.louisleung.springboot.schedulermicroservice.controllers;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.SubmittedTask;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import com.louisleung.springboot.schedulermicroservice.services.TaskResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/api/task")
public class TaskController {

    private TaskServiceImpl taskService;
    private TaskResourceAssembler taskResourceAssembler;

    @Autowired
    public TaskController(TaskServiceImpl taskService, TaskResourceAssembler taskResourceAssembler) {
        this.taskService = taskService;
        this.taskResourceAssembler = taskResourceAssembler;
    }

    /*
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Task> registerTask(@RequestBody SubmittedTask submittedTask) {
        Task task = taskService.save(submittedTask);
        return taskResourceAssembler.toResource(task);
    }
    */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Task> registerTask(@RequestParam("datetime")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") Date dateTime,
                                       @RequestParam("duration") long duration) {
        System.out.println(dateTime);
        System.out.println(dateTime.getTime());
        Task task = taskService.save(new Task(dateTime, duration));
        return this.taskResourceAssembler.toResource(task);
//        return null;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<Task> getTasks() {
        return taskResourceAssembler.toResource(taskService.findAll());
    }

    @ExceptionHandler({DateTimeException.class, InvalidFormatException.class, IllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Date time, duration, and ID must be valid.")
    public String handleBadInput(Exception e) {
        return e.getMessage();
    }
}
