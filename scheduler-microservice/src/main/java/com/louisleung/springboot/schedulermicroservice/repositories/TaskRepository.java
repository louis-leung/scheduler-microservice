package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,Integer> {
    Task findByReadableId(Integer readableId);
    List<Task> findAllByOrderByDueTimeInMillis();
}
