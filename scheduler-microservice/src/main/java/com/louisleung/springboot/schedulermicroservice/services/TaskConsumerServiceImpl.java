package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.exceptions.TaskConsumerNotRegisteredException;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumerServiceImpl implements TaskConsumerService {
    @Autowired
    TaskConsumerRepository taskConsumerRepository;

    @Override
    public TaskConsumer retrieve(Integer id) throws TaskConsumerNotRegisteredException {
        TaskConsumer taskConsumer = taskConsumerRepository.findByReadableId(id);
        if (taskConsumer == null) {
            throw new TaskConsumerNotRegisteredException(id);
        }
        return taskConsumer;
    }

    @Override
    public TaskConsumer save(Integer id) {
        return taskConsumerRepository.save(new TaskConsumer(id));
    }

    @Override
    public long delete(Integer id) {
        return taskConsumerRepository.deleteByReadableId(id);
    }

    @Override
    public TaskConsumer update(TaskConsumer taskConsumer) {
       return taskConsumerRepository.save(taskConsumer);
    }
}
