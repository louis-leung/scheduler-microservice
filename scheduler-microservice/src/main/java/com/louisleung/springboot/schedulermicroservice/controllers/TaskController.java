package com.louisleung.springboot.schedulermicroservice.controllers;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.louisleung.springboot.schedulermicroservice.errors.CustomHttpErrorResponse;
import com.louisleung.springboot.schedulermicroservice.exceptions.ExpiredTaskException;
import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.services.Scheduler;
import com.louisleung.springboot.schedulermicroservice.services.TaskResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.TaskServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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

    @ApiOperation(value="Create a task with due date (local time) and duration (milliseconds)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "CREATED"),
                    @ApiResponse(code = 400, message = "TASK ALREADY EXPIRED"),
                    @ApiResponse(code = 404, message = "INPUT MALFORMED")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Task> registerTask(@RequestParam("datetime")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") Date dateTime,
                                       @RequestParam("duration") long duration) throws ExpiredTaskException {
        Task task = taskService.save(new Task(dateTime, duration));
        return this.taskResourceAssembler.toResource(task);
    }

    @ApiOperation(value="Retrieve all tasks")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<Task> getTasks() {
        Scheduler.markExpired();
        return taskResourceAssembler.toResource(taskService.findAll());
    }

    @ExceptionHandler({DateTimeException.class, InvalidFormatException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)//, reason = "Date time must be in yyyy-MM-dd'T'HH:mm:ss.SSS format and duration must be an integer")
    public CustomHttpErrorResponse handleBadInput(Exception e) {
        return new CustomHttpErrorResponse("Date time must be in yyyy-MM-dd'T'HH:mm:ss.SSS format and duration must be an integer", HttpStatus.BAD_REQUEST);
    }
}
