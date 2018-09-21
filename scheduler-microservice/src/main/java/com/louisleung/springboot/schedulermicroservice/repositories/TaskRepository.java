package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {

    /* Custon MongoDB JSON query to sort available tasks by "Closest Deadline First". */
    @Query("{status: {$in: [\"AWAITING_ASSIGNMENT\"]}}).sort({dueTimeInMillis:-1}")
    List<Task> findAllTasksAwaitingAssignmentOrderByDueTimeInMillis();

    /* Query to detect which tasks are expired based on if their latest possible start time has passed. */
    @Query("{\"latestStartTimeInMillis\" : {$lt : ?0}}")
    List<Task> findExpiredTasks(long currTime);


}
