package com.darrendev.restclient.client;

import com.darrendev.restclient.configuration.ClientTestConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes =   ClientTestConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DockerIntegrationClientTest {

    Logger logger = LoggerFactory.getLogger(DockerIntegrationClientTest.class);
    private static  ObjectMapper mapper = new ObjectMapper();

    @Autowired
    RestFluxClient restTaskClient;

    @Autowired
    GenericContainer toDoApp;


    @Before
    public void init() {
        logger.info("Start up todo webapp container");
        toDoApp.start();
    }

    @After
    public void tearDown() {
        System.out.println("Shutdown todo webapp container");
        toDoApp.stop();
    }



    @Test
    public void test1_emptyGet_1() {
        List<JsonNode> tasks = restTaskClient.getAll();
        assert(tasks.isEmpty());
    }

    @Test
    public void test2_postAction() throws JsonProcessingException {
        JsonNode result = postJson("test");
        System.out.println(result.toString());
        assert(result.get("description").toString().equalsIgnoreCase("\"test\""));
    }



    @Test
    public void test3_getAction() throws JsonProcessingException {
        JsonNode result = postJson("test");
        System.out.println(result.toString());
        JsonNode pt = restTaskClient.getById(1);
        assert(pt.get("id").toString().equalsIgnoreCase("1"));
        assert(pt.get("description").toString().equalsIgnoreCase("\"test\""));

    }

    @Test
    public void test4_updateAction() throws JsonProcessingException {
        JsonNode resultObj = postJson("test2");
        String jsonString2 = "{\"id\": " + resultObj.get("id") + ", \"description\": \"test3\"}";
        JsonNode updatedObj = mapper.readTree(jsonString2);
        JsonNode result = restTaskClient.updateAction(updatedObj);
        System.out.println(result.toString());
        JsonNode gt = restTaskClient.getById(result.get("id").intValue());
        assert(gt.get("description").toString().equalsIgnoreCase("\"test3\""));

    }

    @Test
    public void test5_deleteAction() throws JsonProcessingException {
        JsonNode result = postJson("test");
        String res = restTaskClient.deleteAction(result);
        assertTrue(res.contains("deleted"));
        List<JsonNode> tasks = restTaskClient.getAll();
        assert(tasks.isEmpty());
    }


    private JsonNode postJson(String description) throws JsonProcessingException {
        String jsonString = "{\"description\": \"" + description + "\"}";
        JsonNode actualObj = mapper.readTree(jsonString);
        return restTaskClient.postAction(actualObj);
    }



}
