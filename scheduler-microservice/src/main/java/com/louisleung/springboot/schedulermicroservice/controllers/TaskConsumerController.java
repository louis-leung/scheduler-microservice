package com.louisleung.springboot.schedulermicroservice.controllers;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskConsumerRepository;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api/taskConsumer")
public class TaskConsumerController {

    private TaskConsumerRepository taskConsumerRepository;

    public TaskConsumerController(TaskConsumerRepository taskConsumerRepository) {
        this.taskConsumerRepository = taskConsumerRepository;
    }

    @PostMapping
    public void registerTaskConsumer(@RequestParam("consumerId") Integer consumerId) {
        taskConsumerRepository.save(new TaskConsumer(consumerId));
    }


    @PostMapping(path="/{taskConsumerId}")
    public List<Integer> queryForTasks(@PathVariable Integer taskConsumerId) {
        TaskConsumer taskConsumer = taskConsumerRepository.findByReadableId(taskConsumerId);
        List<Integer> tasks = Scheduler.getTasks();
        return tasks;
    }

}
