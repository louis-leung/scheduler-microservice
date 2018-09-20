package com.louisleung.springboot.schedulermicroservice.controllers;

import com.louisleung.springboot.schedulermicroservice.models.Report;
import com.louisleung.springboot.schedulermicroservice.services.ReportResourceAssembler;
import com.louisleung.springboot.schedulermicroservice.services.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


//TODO: Say produces Media Type JSON?
@RestController
@RequestMapping(path="/api")
public class HomeController {

    public static final String HOME_BASE_URI = "/api";

    private ReportResourceAssembler reportResourceAssembler;

    @Autowired
    public HomeController(ReportResourceAssembler reportResourceAssembler) {
        this.reportResourceAssembler = reportResourceAssembler;
    }

    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    HomeResource homePage() {
        HomeResource resource = new HomeResource();
        resource.add(linkTo(TaskConsumerController.class).withRel("Task Consumer"));
        resource.add(linkTo(TaskController.class).withRel("Task"));
        return resource;
    }

    @GetMapping(path="/report", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Resource<Report> getReport() {
        return this.reportResourceAssembler.toResource(Scheduler.generateReport());

    }

    private class HomeResource extends ResourceSupport {
        public HomeResource() {

        }
    }
}

