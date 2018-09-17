package com.louisleung.springboot.schedulermicroservice.controllers;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskConsumerRepository;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Scheduler {
    private static TaskRepository taskRepository;
    private static TaskConsumerRepository taskConsumerRepository;

    public Scheduler(TaskRepository taskRepository, TaskConsumerRepository taskConsumerRepository) {
        this.taskRepository = taskRepository;
        this.taskConsumerRepository = taskConsumerRepository;
    }

    public static List<Integer> getTasks() {
        /* Here, we give a task consumer all possible tasks it could consume, since we want to maximize the number
           of tasks we can offload to the consumer.
         */
        System.out.println("1");
        List<Task> allTasks = taskRepository.findAllByOrderByDueTimeInMillis();
        System.out.println("2");
        List<Task> assignedTasks = new ArrayList<>();
        System.out.println("3");
        List<Integer> assignedTaskNumbers = new ArrayList<>();
        System.out.println("4");
        for (Task task : allTasks) {
            assignedTasks.add(task);
            assignedTaskNumbers.add(task.getReadableId());
        }
        System.out.println("5");
        return assignedTaskNumbers;

        //return taskRepository.findFirstByOrderByDueTimeInMillis();
    }
}
