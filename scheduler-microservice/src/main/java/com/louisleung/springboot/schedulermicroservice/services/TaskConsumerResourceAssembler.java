package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.controllers.TaskConsumerController;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
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
}
