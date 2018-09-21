package com.louisleung.springboot.schedulermicroservice.controllers;


import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.exceptions.TaskConsumerNotRegisteredException;
import com.louisleung.springboot.schedulermicroservice.services.Scheduler;
import com.louisleung.springboot.schedulermicroservice.services.TaskConsumerResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.TaskConsumerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/taskConsumer")
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

    @ApiOperation(value="Returns all registered task consumers")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
            }
    )
    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<TaskConsumer> getTaskConsumers() {
        return tcResourceAssembler.toResource(taskConsumerService.findAll());
    }

    @ApiOperation(value="Creates a new task consumer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "CREATED")
            }
    )
    @PostMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<TaskConsumer> registerTaskConsumer() {
        TaskConsumer newConsumer = taskConsumerService.create();
        return this.tcResourceAssembler.toResource(newConsumer);
    }

    @ApiOperation(value="Retrieve info from a specific task consumer")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 404, message = "CONSUMER DOESN'T EXIST")
            }
    )
    @GetMapping(path="/{taskConsumerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource<TaskConsumer> retrieveTaskConsumer(@PathVariable String taskConsumerId) throws TaskConsumerNotRegisteredException {
        TaskConsumer consumer = taskConsumerService.retrieve(taskConsumerId);
        return this.tcResourceAssembler.toResource(consumer);
    }

    @ApiOperation(value="Make a specific task consumer query for tasks")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 404, message = "CONSUMER DOESN'T EXIST")
            }
    )
    @PostMapping(path="/{taskConsumerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<TaskConsumer> queryForTasks(@PathVariable String taskConsumerId) throws TaskConsumerNotRegisteredException {
        Scheduler.markExpired();
        TaskConsumer taskConsumer = taskConsumerService.retrieve(taskConsumerId);
        Scheduler.getAndAssignTasks(taskConsumer);
        return this.tcResourceAssembler.toResource(taskConsumer);
    }
}
