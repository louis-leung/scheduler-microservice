package com.louisleung.springboot.schedulermicroservice.services;

import com.louisleung.springboot.schedulermicroservice.controllers.HomeController;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskConsumerController;
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
public class TaskConsumerResourceAssembler implements ResourceAssembler<TaskConsumer, Resource<TaskConsumer>> {
    @Override
    public Resource<TaskConsumer> toResource(TaskConsumer taskConsumer) {
        Resource<TaskConsumer> resource = new Resource<>(taskConsumer);
        /* Where to find and utilize this task consumer we just created. */
        resource.add(linkTo(TaskConsumerController.class).slash(taskConsumer.getId()).withRel("newly created task consumer"));
        resource.add(linkTo(TaskConsumerController.class).withRel("task consumers"));
        resource.add(linkTo(Task.class).withRel("tasks"));
        resource.add(linkTo(HomeController.class).slash("report").withRel("report"));
        return resource;
    }

    public Resources<TaskConsumer> toResource(List<TaskConsumer> taskConsumers) {
        Resources<TaskConsumer> resources = new Resources<>(taskConsumers);
        resources.add(linkTo(TaskConsumerController.class).withRel("task consumers"));
        resources.add(linkTo(Task.class).withRel("tasks"));
        resources.add(linkTo(HomeController.class).slash("report").withRel("report"));
        return resources;
    }
}
