package com.louisleung.springboot.schedulermicroservice;

import com.louisleung.springboot.schedulermicroservice.models.Scheduler;
import com.louisleung.springboot.schedulermicroservice.repositories.SchedulerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {
    private SchedulerRepository schedulerRepository;

    public DbSeeder(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }
    @Override
    public void run(String... strings) throws Exception {
        Scheduler scheduler = new Scheduler(29);
        this.schedulerRepository.deleteAll();
        this.schedulerRepository.save(scheduler);
    }

}
