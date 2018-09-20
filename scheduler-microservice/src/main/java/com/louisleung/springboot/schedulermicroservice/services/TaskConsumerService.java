package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.exceptions.TaskConsumerNotRegisteredException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskConsumerService {
    TaskConsumer retrieve(String id) throws TaskConsumerNotRegisteredException;
    TaskConsumer create();
    TaskConsumer update(TaskConsumer taskConsumer);
    void delete(String id);
    void deleteAll();
    List<TaskConsumer> findAll();
}
