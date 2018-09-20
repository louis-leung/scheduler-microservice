package com.louisleung.springboot.schedulermicroservice.services;


import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    TaskRepository taskRepository;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findValidTasksOrdered() {
        return taskRepository.findAllTasksAwaitingAssignmentOrderByDueTimeInMillis();
    }

    @Override
    public void delete(String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findExpiredTasks(long currTime) {
        return taskRepository.findExpiredTasks(currTime);
    }
}
