package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {

    @Query("{status: {$in: [\"AWAITING_ASSIGNMENT\"]}}).sort({dueTimeInMillis:-1}")
    List<Task> findAllTasksAwaitingAssignmentOrderByDueTimeInMillis();


}
