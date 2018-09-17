package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface TaskConsumerRepository extends MongoRepository<TaskConsumer,Integer>{
   TaskConsumer findByReadableId(Integer readableId);
}
