package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskConsumerRepository extends MongoRepository<TaskConsumer,String>{

    /* Custom MongoDB JSON query to find all consumers that should be available. */
    @Query("{\"epochTimeWhenAvailable\" : {$lte : ?0}}")
    List<TaskConsumer> findAllFreeTCs(long epochTime);

}
