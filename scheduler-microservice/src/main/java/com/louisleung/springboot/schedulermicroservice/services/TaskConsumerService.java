package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.exceptions.TaskConsumerNotRegisteredException;
import org.springframework.stereotype.Service;

@Service
public interface TaskConsumerService {
    TaskConsumer retrieve(Integer readableId) throws TaskConsumerNotRegisteredException;
    TaskConsumer save(Integer readableId);
    long delete(Integer readableId);
}
