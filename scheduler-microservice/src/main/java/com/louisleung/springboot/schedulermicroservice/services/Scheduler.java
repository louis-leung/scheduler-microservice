package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskStatus;
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

    @Autowired
    public Scheduler(TaskRepository taskRepository, TaskConsumerRepository taskConsumerRepository) {
        this.taskRepository = taskRepository;
        this.taskConsumerRepository = taskConsumerRepository;
    }

    public static List<Task> getTasks() {
        /* Here, we give a task consumer all possible tasks it could consume, since we want to maximize the number
           of tasks we can offload to the consumer.
         */
        List<Task> allTasks = taskRepository.findAllByOrderByDueTimeInMillis();
        List<Task> assignedTasks = new ArrayList<>();
        /* This is the projected time to complete all the tasks we've assigned thus far. */
        long projectedTime = System.currentTimeMillis();
        for (Task task : allTasks) {
            /* Task expired, mark as such and continue. */
            if (System.currentTimeMillis() + task.getDuration() > task.getDueTimeInMillis()) {
                task.setStatus(TaskStatus.EXPIRED);
                continue;
            }
            /* This task is still completable, assign to consumer. */
            if (projectedTime + task.getDuration() < task.getDueTimeInMillis()) {
                assignedTasks.add(task);
                projectedTime += task.getDueTimeInMillis();
                task.setStatus(TaskStatus.ASSIGNED);
            }
            /* Task not feasible for this consumer. */
            else {

            }
        }
        return assignedTasks;

        //return taskRepository.findFirstByOrderByDueTimeInMillis();
    }
}
