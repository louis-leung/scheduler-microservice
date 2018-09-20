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
    private static TaskServiceImpl taskService;
    private static TaskConsumerServiceImpl taskConsumerService;

    @Autowired
    public Scheduler(TaskConsumerServiceImpl taskConsumerService, TaskServiceImpl taskService) {
        this.taskService = taskService;
        this.taskConsumerService = taskConsumerService;
    }

    public static List<Task> getTasks() {
        /* Here, we give a task consumer all possible tasks it could consume, since we want to maximize the number
           of tasks we can offload to the consumer.
         */
        List<Task> allTasks = taskService.findValidTasksOrdered();


        List<Task> assignedTasks = new ArrayList<>();
        /* This is the projected time to complete all the tasks we've assigned thus far. */
        long projectedTime = System.currentTimeMillis();
        for (Task task : allTasks) {
            System.out.printf("Current task: %s",task);
            /* Task expired, mark as such and continue. */
            if (System.currentTimeMillis() + task.getDuration() > task.getDueTimeInMillis()) {
                System.out.println("tASK EXPIRED, marking and saving");
                task.setStatus(TaskStatus.EXPIRED);
                taskService.save(task);
                continue;
            }
            /* This task is still completable, assign to consumer. */
            if (projectedTime + task.getDuration() < task.getDueTimeInMillis()) {
                System.out.println("TASK ASSIGNED");
                assignedTasks.add(task);
                projectedTime += task.getDuration();
                task.setStatus(TaskStatus.ASSIGNED);
                taskService.save(task);
            }
            /* Task not feasible for this consumer. */
            else {

            }
        }
        System.out.println("Returning assigned tasks:");
        System.out.println(assignedTasks.toString());
        return assignedTasks;

        //return taskRepository.findFirstByOrderByDueTimeInMillis();
    }
}
