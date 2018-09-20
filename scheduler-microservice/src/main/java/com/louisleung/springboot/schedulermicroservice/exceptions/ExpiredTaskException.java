package com.louisleung.springboot.schedulermicroservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class ExpiredTaskException extends Exception{
    public ExpiredTaskException() {
        super(String.format("Task is already expired"));
    }
}
