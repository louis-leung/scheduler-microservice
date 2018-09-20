package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/* Component without prototype makes Spring Context create the singleton bean class. */
@Repository
public interface TaskConsumerRepository extends MongoRepository<TaskConsumer,String>{
//    @Query("{\"dateWhenAvailable\" : {$lte : new Date(Date())}}")
    @Query("{\"epochTimeWhenAvailable\" : {$lte : ?0}}")
    public List<TaskConsumer> findAllFreeTCs(long epochTime);

}
