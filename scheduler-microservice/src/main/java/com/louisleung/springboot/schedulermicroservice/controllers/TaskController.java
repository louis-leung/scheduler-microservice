package com.louisleung.springboot.schedulermicroservice.controllers;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.louisleung.springboot.schedulermicroservice.exceptions.ExpiredTaskException;
import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.services.TaskResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.util.Date;

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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Task> registerTask(@RequestParam("datetime")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") Date dateTime,
                                       @RequestParam("duration") long duration) throws ExpiredTaskException {
        Task task = taskService.save(new Task(dateTime, duration));
        return this.taskResourceAssembler.toResource(task);
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
