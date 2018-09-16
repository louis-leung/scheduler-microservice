package com.louisleung.springboot.schedulermicroservice.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Scheduler {
    @Id
    private String id;
    private Integer readableId;

    public Scheduler(Integer readableId) {
        this.readableId = readableId;
    }

    public String getId() {
        return id;
    }

    public Integer getReadableId() {
        return readableId;
    }
}
