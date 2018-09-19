package com.louisleung.springboot.schedulermicroservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class TaskConsumerNotRegisteredException extends Exception{
    public TaskConsumerNotRegisteredException(Integer consumerId) {
        super(String.format("Task Consumer with Id %d has not been registered yet",consumerId));
    }
}
