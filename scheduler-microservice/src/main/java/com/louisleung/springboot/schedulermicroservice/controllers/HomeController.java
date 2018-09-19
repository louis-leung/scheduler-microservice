package com.louisleung.springboot.schedulermicroservice.controllers;

import org.springframework.beans.factory.annotation.Value;
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

//    @Value("${user.role}")
//    private String role;

//    @GetMapping(path="/configTest")
//    String configTest() {
//        return role;
//    }

    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    HomeResource homePage() {
        HomeResource resource = new HomeResource();
        resource.add(linkTo(TaskConsumerController.class).withRel("Task Consumer"));
        resource.add(linkTo(TaskController.class).withRel("Task"));
        return resource;
    }

    private class HomeResource extends ResourceSupport {
        public HomeResource() {

        }
    }
}

