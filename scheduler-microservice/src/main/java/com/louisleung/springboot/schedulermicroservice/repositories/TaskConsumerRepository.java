package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/* Component without prototype makes Spring Context create the singleton bean class. */
@Repository
public interface TaskConsumerRepository extends MongoRepository<TaskConsumer,String>{
}
