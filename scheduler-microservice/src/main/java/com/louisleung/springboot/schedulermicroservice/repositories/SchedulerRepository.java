package com.louisleung.springboot.schedulermicroservice.repositories;

import com.louisleung.springboot.schedulermicroservice.models.Scheduler;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository extends MongoRepository<Scheduler,Integer> {
    Scheduler findByReadableId(Integer readableId);
    //Scheduler save(Scheduler schedule);
}
