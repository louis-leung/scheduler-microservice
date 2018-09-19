package com.louisleung.springboot.schedulermicroservice.controllers;


import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.exceptions.TaskConsumerNotRegisteredException;
import com.louisleung.springboot.schedulermicroservice.services.Scheduler;
import com.louisleung.springboot.schedulermicroservice.services.TaskConsumerResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.TaskConsumerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

@RestController
@RequestMapping(path="/api/taskConsumer")
//@EnableHypermediaSupport
public class TaskConsumerController {

    public static final String BASE_URI = "/api/taskConsumer";

    private TaskConsumerServiceImpl taskConsumerService;

    private TaskConsumerResourceAssembler tcResourceAssembler;

    @Autowired
    public TaskConsumerController(TaskConsumerServiceImpl taskConsumerService,
                                  TaskConsumerResourceAssembler tcResourceAssembler) {
        this.taskConsumerService = taskConsumerService;
        this.tcResourceAssembler = tcResourceAssembler;
    }

    @PostMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<TaskConsumer> registerTaskConsumer(@RequestParam("taskConsumerId") Integer taskConsumerId) {
        System.out.println("ABOUT TO REGISTER CONSUMER");
        TaskConsumer newConsumer = taskConsumerService.save(taskConsumerId);
        return this.tcResourceAssembler.toResource(newConsumer);
    }

    @GetMapping(path="/{taskConsumerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<TaskConsumer> retrieveTaskConsumers(@PathVariable Integer taskConsumerId) throws TaskConsumerNotRegisteredException {
        TaskConsumer consumer = taskConsumerService.retrieve(taskConsumerId);
        return this.tcResourceAssembler.toResource(consumer);
    }



    @PostMapping(path="/{taskConsumerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> queryForTasks(@PathVariable Integer taskConsumerId) throws TaskConsumerNotRegisteredException {
        TaskConsumer taskConsumer = taskConsumerService.retrieve(taskConsumerId);
        List<Task> tasks = Scheduler.getTasks();
        return tasks;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Task Consumer ID must be an integer")
    public String handleBadInput(Exception e) {
        return e.getMessage();
    }
}
