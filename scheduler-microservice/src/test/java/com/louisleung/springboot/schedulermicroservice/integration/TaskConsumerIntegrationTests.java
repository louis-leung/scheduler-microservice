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
public class TaskConsumerIntegrationTests {

    //@Value("${local.server.port}")
    //private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TaskConsumerServiceImpl taskConsumerService;


    private MockMvc mockMvc;
    private final Integer TEST_CONSUMER_ID = 9999;
    private final String INVALID_TC_PARAM = "asdf";
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
    public void WhenICreateANewTCWithValidParamsIGetCreatedResponseAndPayload() throws Exception{
        String url = new StringBuilder()
                //.append("http://localhost:8080/api/")
                .append(TaskConsumerController.BASE_URI)
                .toString();

        System.out.println(url);

        //String expectedReturn = String.format("{\"readableId\":%d,\"assignedTasks\":null})",TEST_CONSUMER_ID);
        String expectedReturn = TestUtils.asJsonString(new TaskConsumer(TEST_CONSUMER_ID));

        mockMvc.perform(post(url).param("taskConsumerId",TEST_CONSUMER_ID.toString()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedReturn))
                .andDo(print())
                .andReturn();

    }


    @Test
    public void WhenICreateANewTCWithValidParamsTheTCObjectIsPersisted() throws Exception {
        String url = new StringBuilder()
                //.append("/")
                .append(TaskConsumerController.BASE_URI)
                .toString();


        System.out.println(url);
        mockMvc.perform(post(url).param("taskConsumerId",TEST_CONSUMER_ID.toString()))
                .andDo(print());
        assertEquals(TEST_CONSUMER_ID, taskConsumerService.retrieve(TEST_CONSUMER_ID).getReadableId());
        assertNull(taskConsumerService.retrieve(TEST_CONSUMER_ID).getAssignedTasks());
    }

    @Test
    public void WhenITryToCreateANewTCWithInvalidParamTypesIGet400() throws Exception {
        String url = new StringBuilder()
                //.append("/")
                .append(TaskConsumerController.BASE_URI)
                .toString();


        System.out.println(url);
        mockMvc.perform(post(url).param("taskConsumerId",INVALID_TC_PARAM))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void WhenITryToRetreiveAValidTCIGetOKResponseAndPayload() throws Exception {
        String url = new StringBuilder()
                //.append("/")
                .append(TaskConsumerController.BASE_URI)
                .append("/")
                .append(TEST_CONSUMER_ID)
                .toString();


        /* Create the object. */
        TaskConsumer tc = taskConsumerService.save(TEST_CONSUMER_ID);

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
                .append(TEST_CONSUMER_ID)
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
                .append(TEST_CONSUMER_ID)
                .toString();


        mockMvc.perform(post(url))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }


}
