package com.louisleung.springboot.schedulermicroservice.services;

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

@Component
public class TaskConsumerResourceAssembler implements ResourceAssembler<TaskConsumer, Resource<TaskConsumer>> {
    @Override
    public Resource<TaskConsumer> toResource(TaskConsumer taskConsumer) {
        Resource<TaskConsumer> resource = new Resource<>(taskConsumer);
        /* Where to find and utilize this task consumer we just created. */
        resource.add(linkTo(TaskConsumerController.class).slash(taskConsumer.getId()).withRel("Task Consumer"));
        /* Self link. */
        //resource.add(linkTo(TaskConsumerController.class))
        return resource;
    }

    public Resources<TaskConsumer> toResource(List<TaskConsumer> taskConsumers) {
        Resources<TaskConsumer> resources = new Resources<>(taskConsumers);
        resources.add(linkTo(TaskConsumerController.class).withRel("Task Consumers"));
        return resources;
    }
}
