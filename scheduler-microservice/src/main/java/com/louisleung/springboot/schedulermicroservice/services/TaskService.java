package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    List<Task> findAll();
    List<Task> findValidTasksOrdered();
    void delete(String id);
    List<Task> findExpiredTasks(long currTime);
}
