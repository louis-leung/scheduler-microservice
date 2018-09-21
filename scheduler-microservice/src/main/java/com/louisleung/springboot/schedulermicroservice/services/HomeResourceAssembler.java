package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.controllers.HomeController;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskConsumerController;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskController;
import com.louisleung.springboot.schedulermicroservice.models.Report;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class HomeResourceAssembler implements ResourceAssembler<String, Resource<String>> {
    @Override
    public Resource<String> toResource(String name) {
        Resource<String> resource = new Resource<String>(name);
        /* Self link. */
        resource.add(linkTo(HomeController.class).slash("report").withRel("self"));
        resource.add(linkTo(TaskConsumerController.class).withRel("task consumers"));
        resource.add(linkTo(TaskController.class).withRel("tasks"));
        return resource;
    }
}
