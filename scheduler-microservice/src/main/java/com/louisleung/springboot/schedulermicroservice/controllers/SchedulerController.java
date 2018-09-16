package com.louisleung.springboot.schedulermicroservice.controllers;

import com.louisleung.springboot.schedulermicroservice.models.Scheduler;
import com.louisleung.springboot.schedulermicroservice.repositories.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO: Say produces Media Type JSON?
@RestController
@RequestMapping(path="/api/scheduler")
public class SchedulerController {

    //@Autowired
    //private static SchedulerRepository schedulerRepository;
    private SchedulerRepository schedulerRepository;

    public SchedulerController(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @PostMapping
    ResponseEntity<Void> createScheduler() {
       Scheduler scheduler = schedulerRepository.save(new Scheduler(0));

        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(linkTo(SchedulerController.class).slash(scheduler.getReadableId()).toUri());
        //headers.setLocation(linkTo(SchedulerController.class).slash(scheduler.getReadableId()).withSelfRel());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(path=("/{schedulerId}"))
    ResponseEntity<Scheduler> showScheduler(@PathVariable Integer schedulerId) {
        Scheduler scheduler = this.schedulerRepository.findByReadableId(schedulerId);
        System.out.println("Scheduler ");
        System.out.println(scheduler.getReadableId());
        return new ResponseEntity<Scheduler>(scheduler, HttpStatus.OK);
    }
}

