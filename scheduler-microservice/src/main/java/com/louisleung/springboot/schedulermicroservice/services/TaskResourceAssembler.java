package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.controllers.HomeController;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskConsumerController;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskController;
import com.louisleung.springboot.schedulermicroservice.models.Task;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/* In compliance with RESTful trait HATEOAS, we assemble and return Resources containing links to other URIs along with the
 * data as our response bodies.
 */
@Component
public class TaskResourceAssembler implements ResourceAssembler<Task, Resource<Task>> {
    @Override
    public Resource<Task> toResource(Task task) {
        Resource<Task> resource = new Resource<>(task);
        /* Link to all tasks to view. */
        resource.add(linkTo(TaskController.class).withRel("tasks"));
        resource.add(linkTo(TaskConsumerController.class).withRel("task consumers"));
        resource.add(linkTo(HomeController.class).slash("report").withRel("report"));
        return resource;

    }

    public Resources<Task> toResource(List<Task> task) {
        Resources<Task> resources = new Resources<>(task);
        resources.add(linkTo(TaskController.class).withRel("tasks"));
        resources.add(linkTo(TaskConsumerController.class).withRel("task consumers"));
        resources.add(linkTo(HomeController.class).slash("report").withRel("report"));
        return resources;
    }
}
