package com.louisleung.springboot.schedulermicroservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louisleung.springboot.schedulermicroservice.SchedulerMicroserviceApplication;
import com.louisleung.springboot.schedulermicroservice.TestUtils;
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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Enumeration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SchedulerMicroserviceApplication.class)
@AutoConfigureMockMvc
/* Use testing profile for separate DB. */
@ActiveProfiles("test")
public class TaskConsumerIntegrationTests {

    //@Value("${local.server.port}")
    //private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TaskConsumerServiceImpl taskConsumerService;


    private MockMvc mockMvc;
    private static String testConsumerId;
    private final Integer INVALID_TC_PARAM = 1;

    @Before
    public void setup() {
        System.out.println("Setting up tests.");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void teardown() {
        System.out.println("Tearing down tests.");
        taskConsumerService.deleteAll();
    }

    /* ================
     * POSTing to /api/taskConsumer
     * ================ */

    @Test
    public void WhenICreateANewTCWithValidParamsIGetCreatedResponseAndPayload() throws Exception{
        String url = new StringBuilder()
                //.append("http://localhost:8080/api/")
                .append(TaskConsumerController.BASE_URI)
                .toString();

        System.out.println(url);



        MvcResult returned = mockMvc.perform(post(url))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

    }

    @Test
    public void WhenICreateANewTCWithValidParamsItIsPersisted() throws Exception {
        String url = new StringBuilder()
                //.append("http://localhost:8080/api/")
                .append(TaskConsumerController.BASE_URI)
                .toString();

        System.out.println(url);



        MvcResult returned = mockMvc.perform(post(url))
                .andDo(print())
                .andReturn();

        assertNotNull(taskConsumerService.findAll());
    }

    @Test
    public void WhenITryToRetreiveAValidTCIGetOKResponseAndPayload() throws Exception {
        /* Create the object. */
        TaskConsumer tc = taskConsumerService.create();

        String url = new StringBuilder()
                //.append("/")
                .append(TaskConsumerController.BASE_URI)
                .append("/")
                .append(tc.getId())
                .toString();


        String expectedReturn = TestUtils.asJsonString(tc);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedReturn))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void WhenITryToRetreiveAnInvalidTCIGetNotFoundResponse() throws Exception {
        String url = new StringBuilder()
                //.append("/")
                .append(TaskConsumerController.BASE_URI)
                .append("/")
                .append(INVALID_TC_PARAM)
                .toString();


        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void WhenITryToQueryForTasksOnAConsumerThatIsNotRegisteredIGetNotFoundResponse() throws Exception {
        String url = new StringBuilder()
                //.append("/")
                .append(TaskConsumerController.BASE_URI)
                .append("/")
                .append(INVALID_TC_PARAM)
                .toString();


        mockMvc.perform(post(url))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }


}
