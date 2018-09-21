package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.exceptions.TaskConsumerNotRegisteredException;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskConsumerServiceImpl implements TaskConsumerService {
    @Autowired
    TaskConsumerRepository taskConsumerRepository;

    @Override
    public TaskConsumer retrieve(String id) throws TaskConsumerNotRegisteredException {
        TaskConsumer taskConsumer = taskConsumerRepository.findById(id).orElse(null);
        if (taskConsumer == null) {
            throw new TaskConsumerNotRegisteredException(id);
        }
        return taskConsumer;
    }

    @Override
    public TaskConsumer create() {
        return taskConsumerRepository.save(new TaskConsumer());
    }


    public TaskConsumer create(TaskConsumer taskConsumer) {
        return taskConsumerRepository.save(taskConsumer);
    }

    @Override
    public void delete(String id) {
        taskConsumerRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        taskConsumerRepository.deleteAll();
    }

    @Override
    public TaskConsumer update(TaskConsumer taskConsumer) {
       return taskConsumerRepository.save(taskConsumer);
    }

    @Override
    public List<TaskConsumer> findAll() {
        return taskConsumerRepository.findAll();
    }

    @Override
    public List<TaskConsumer> findAllFreeTCs(long currTime) {
        return taskConsumerRepository.findAllFreeTCs(currTime);
    }
}
