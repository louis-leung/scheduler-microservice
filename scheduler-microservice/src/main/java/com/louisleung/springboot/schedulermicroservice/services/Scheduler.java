package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.Report;
import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.models.TaskStatus;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskConsumerRepository;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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

        List<TaskConsumer> availableTCs = taskConsumerService.findAllFreeTCs(System.currentTimeMillis());
        for (TaskConsumer availableTC : availableTCs) {
            System.out.printf("TC IS AVAILABLE: %s\n\n\n",availableTC.getId());
        }
        Report report = generateSchedule(availableTCs);
        if (report == null) {
            return new Report(Report.ReportStatus.INABLE_TO_COMPLETE_GIVEN_CURRENT_STATE);
        }
        return report;
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
            List<Task> tasksForSingleTC = assignTasks(availableTC, availableTasks);
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
           return null;
       }

    }

    public static List<Task> assignTasks(TaskConsumer taskConsumer, List<Task> tasks) {

        List<Task> assignedTasks = new ArrayList<>();
        /* This is the projected time to complete all the tasks we've assigned thus far. */
        long projectedTime = System.currentTimeMillis();
        for (Task task : tasks) {
//            System.out.printf("Current task: %s",task);
            /* This task is still completable, assign to consumer. */
            if (projectedTime + task.getDuration() < task.getDueTimeInMillis()) {
                System.out.println("TASK ASSIGNED");
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
                /* After we assign a task, we assume the consumer will accomplish all tasks and we no longer need
                   to worry about it. */
                taskService.delete(task.getId());
            }
            /* Task not feasible for this consumer. */
            else {

            }
        }
        taskConsumer.setAssignedTasks(assignedTasks);
        taskConsumerService.update(taskConsumer);

        //return taskRepository.findFirstByOrderByDueTimeInMillis();
    }
}
