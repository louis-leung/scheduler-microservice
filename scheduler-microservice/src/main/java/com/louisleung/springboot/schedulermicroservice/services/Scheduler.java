package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.Report;
import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.models.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/* This class contains the main scheduling logic. */
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

        /* Create two copies of the list of tasks to be completed. */
        List<Task> tasksLeft = new ArrayList<>();
        List<Task> toBeCompletedTasks = new ArrayList<>();
        toBeCompletedTasks.addAll(taskService.findValidTasksOrdered());
        tasksLeft.addAll(toBeCompletedTasks);

        List<Task> allAssignedTasks = new ArrayList<>();

        Map<TaskConsumer, List<Task>> assignments = new HashMap<>();

        /* For each available consumer, assign it as many tasks as possible from the tasks we have left. */
        for (TaskConsumer availableTC : availableTCs) {
            List<Task> tasksForSingleTC = getTasks(tasksLeft);
            allAssignedTasks.addAll(tasksForSingleTC);
            tasksLeft.removeAll(tasksForSingleTC);
            assignments.put(availableTC, tasksForSingleTC);
        }

        /* Check if we've completed all the tasks. */
       if (allAssignedTasks.containsAll(toBeCompletedTasks)) {
           /* We've completed all our tasks, create report. */
           return new Report(assignments);
       }
       else {
           /* Unable to complete tasks, create report. */
           return new Report(Report.ReportStatus.UNABLE_TO_COMPLETE_GIVEN_CURRENT_STATE);
       }

    }

    /* Returns a potential sequence of tasks that could be done at this moment, without updating any actual state. */
    public static List<Task> getTasks(List<Task> tasks) {
        List<Task> assignedTasks = new ArrayList<>();
        /* This is the projected time to complete all the tasks we've assigned thus far. */
        long projectedTime = System.currentTimeMillis();
        for (Task task : tasks) {
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

    /* Assigns tasks to a specific task consumer using getTasks method, updating state in consumer and tasks. */
    public static void getAndAssignTasks(TaskConsumer taskConsumer) {
        List<Task> assignedTasks = getTasks(taskService.findValidTasksOrdered());
        for (Task task : assignedTasks) {
            task.setStatus(TaskStatus.ASSIGNED);
            taskService.save(task);
        }
        taskConsumer.setAssignedTasks(assignedTasks);
        taskConsumerService.update(taskConsumer);
    }

    /* Marks expired tasks. */
    public static void markExpired() {
        List<Task> expired = taskService.findExpiredTasks(System.currentTimeMillis());
        for (Task task : expired) {
            task.setStatus(TaskStatus.EXPIRED);
            taskService.save(task);
        }

    }
}
