package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.controllers.HomeController;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskConsumerController;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskController;
import com.louisleung.springboot.schedulermicroservice.models.Report;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ReportResourceAssembler implements ResourceAssembler<Report, Resource<Report>>{
    @Override
    public Resource<Report> toResource(Report report) {
        Resource<Report> resource = new Resource<>(report);
        /* Where to find and utilize this task consumer we just created. */
        resource.add(linkTo(HomeController.class).slash("report").withRel("self"));
        /* Self link. */
        //resource.add(linkTo(TaskConsumerController.class))
        return resource;
    }
}
