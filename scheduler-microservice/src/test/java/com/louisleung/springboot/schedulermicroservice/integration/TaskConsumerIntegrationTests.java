package com.louisleung.springboot.schedulermicroservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisleung.springboot.schedulermicroservice.SchedulerMicroserviceApplication;
import com.louisleung.springboot.schedulermicroservice.controllers.TaskConsumerController;
import com.louisleung.springboot.schedulermicroservice.models.TaskConsumer;
import com.louisleung.springboot.schedulermicroservice.repositories.TaskConsumerRepository;
import com.louisleung.springboot.schedulermicroservice.services.TaskConsumerService;
import com.louisleung.springboot.schedulermicroservice.services.TaskConsumerServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SchedulerMicroserviceApplication.class)
@AutoConfigureMockMvc
//@WebAppConfiguration
//@EnableAutoConfiguration
//@ContextConfiguration
//@WebMvcTest
//@DataMongoTest
public class TaskConsumerIntegrationTests {

    //@Value("${local.server.port}")
    //private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TaskConsumerServiceImpl taskConsumerService;

    private MockMvc mockMvc;
    private final Integer TEST_CONSUMER_ID = 9999;
    TaskConsumer taskConsumer;

    @Before
    public void setup() {
        System.out.println("Setting up tests.");
        taskConsumerService.delete(TEST_CONSUMER_ID);
        this.taskConsumer = new TaskConsumer(TEST_CONSUMER_ID);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void teardown() {
        System.out.println("Tearing down tests.");
        taskConsumerService.delete(TEST_CONSUMER_ID);
    }

    /* ================
     * POSTing to /api/taskConsumer
     * ================ */

    @Test
    public void WhenIPostToTaskConsumerEndpointIGetCreatedResponseAndPayload() throws Exception{
        System.out.println("Running first test");
        String url = new StringBuilder()
                //.append("http://localhost:8080/api/")
                .append(TaskConsumerController.BASE_URI)
                .toString();

        System.out.println(url);

        String expectedReturn = String.format("{\"readableId\":%d,\"assignedTasks\":null})",TEST_CONSUMER_ID);

        mockMvc.perform(post(url).param("taskConsumerId",TEST_CONSUMER_ID.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedReturn))
                .andDo(print())
                .andReturn();

    }


    @Test
    public void WhenIPostToTaskConsumerEndpointTheTCObjectIsPersisted() throws Exception {
        System.out.println("Running second test");
        String url = new StringBuilder()
                //.append("/")
                .append(TaskConsumerController.BASE_URI)
                .toString();


        System.out.println(url);
        mockMvc.perform(post(url).param("taskConsumerId",TEST_CONSUMER_ID.toString()));
        System.out.println("Post successful");
        assertEquals(TEST_CONSUMER_ID, taskConsumerService.retrieve(TEST_CONSUMER_ID).getReadableId());
        assertNull(taskConsumerService.retrieve(TEST_CONSUMER_ID).getAssignedTasks());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
