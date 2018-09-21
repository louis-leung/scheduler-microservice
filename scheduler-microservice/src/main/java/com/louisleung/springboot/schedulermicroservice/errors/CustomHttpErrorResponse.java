package com.louisleung.springboot.schedulermicroservice.errors;

import org.springframework.http.HttpStatus;

public class CustomHttpErrorResponse {
    private String message;
    private HttpStatus status;

    public CustomHttpErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
