package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.Report;
import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.models.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Scheduler {
    private static TaskServiceImpl taskService;
    private static TaskConsumerServiceImpl taskConsumerService;

    @Autowired
    public Scheduler(TaskConsumerServiceImpl taskConsumerService, TaskServiceImpl taskService) {
        this.taskService = taskService;
        this.taskConsumerService = taskConsumerService;
    }

    public static Report generateReport() {
        /* First find the available consumers, and then attempt to generate a schedule with them. */
        List<TaskConsumer> availableTCs = taskConsumerService.findAllFreeTCs(System.currentTimeMillis());
        return generateSchedule(availableTCs);
    }

    private static Report generateSchedule(List<TaskConsumer> availableTCs) {
        System.out.println("Generating schedule for TCs");

        List<Task> availableTasks = new ArrayList<>();
        List<Task> toBeCompletedTasks = new ArrayList<>();
        toBeCompletedTasks.addAll(taskService.findValidTasksOrdered());
        availableTasks.addAll(toBeCompletedTasks);

        List<Task> allAssignedTasks = new ArrayList<>();

        Map<TaskConsumer, List<Task>> assignments = new HashMap<>();

        /* For each available consumer, assign it all the tasks. */
        for (TaskConsumer availableTC : availableTCs) {
            List<Task> tasksForSingleTC = getTasks(availableTasks);
            allAssignedTasks.addAll(tasksForSingleTC);
            availableTasks.removeAll(tasksForSingleTC);

            assignments.put(availableTC, tasksForSingleTC);
        }

        /* Check if we've completed all the tasks. */
       if (allAssignedTasks.containsAll(toBeCompletedTasks)) {
           System.out.println("Schedule successfully generated");
           /* We've completed all our tasks, create report. */
           return new Report(assignments);
       }
       else {
           return new Report(Report.ReportStatus.INABLE_TO_COMPLETE_GIVEN_CURRENT_STATE);
       }

    }

    public static List<Task> getTasks(List<Task> tasks) {

        List<Task> assignedTasks = new ArrayList<>();
        /* This is the projected time to complete all the tasks we've assigned thus far. */
        long projectedTime = System.currentTimeMillis();
        for (Task task : tasks) {
//            System.out.printf("Current task: %s",task);
            /* This task is still completable, assign to consumer. */
            if (projectedTime + task.getDuration() < task.getDueTimeInMillis()) {
                assignedTasks.add(task);
                projectedTime += task.getDuration();
                /* After we assign a task, we assume the consumer will accomplish all tasks and we no longer need
                   to worry about it. */
            }
            /* Task not feasible for this consumer. */
        }
        return assignedTasks;
    }

    public static void getAndAssignTasks(TaskConsumer taskConsumer) {
        List<Task> assignedTasks = getTasks(taskService.findValidTasksOrdered());
        for (Task task : assignedTasks) {
            task.setStatus(TaskStatus.ASSIGNED);
            taskService.save(task);
        }
        taskConsumer.setAssignedTasks(assignedTasks);
        taskConsumerService.update(taskConsumer);
    }


    public static void markExpired() {
        /* Marks expired tasks. */
        List<Task> expired = taskService.findExpiredTasks(System.currentTimeMillis());
        for (Task task : expired) {
            task.setStatus(TaskStatus.EXPIRED);
            taskService.save(task);
        }

    }
}
