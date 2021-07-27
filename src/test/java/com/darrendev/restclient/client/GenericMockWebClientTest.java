package com.darrendev.restclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GenericMockWebClientTest {

    private MockWebServer mockWebServer;


    @Autowired
    private RestFluxClient restFluxClient;

    @Before
    public void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

    }





    @Test
    public void testPostAction() throws JsonProcessingException {
        MockResponse mockResponse = new MockResponse().addHeader("Content-Type","application/json").
                setBody("{\n" +
                        "  \"empty\": true,\n" +
                        "  \"model\": {},\n" +
                        "  \"modelMap\": {\n" +
                        "    \"additionalProp1\": {},\n" +
                        "    \"additionalProp2\": {},\n" +
                        "    \"additionalProp3\": {}\n" +
                        "  },\n" +
                        "  \"reference\": true,\n" +
                        "  \"status\": \"100 CONTINUE\",\n" +
                        "  \"view\": {\n" +
                        "    \"contentType\": \"string\"\n" +
                        "  },\n" +
                        "  \"viewName\": \"string\"\n" +
                        "}").throttleBody(16, 5, TimeUnit.SECONDS).setResponseCode(201);
                mockWebServer.enqueue(mockResponse);
                String postBody = "{\n" +
                        "  \"id\": 0,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}";
                ObjectMapper mapper = new ObjectMapper();
                JsonNode pObj = mapper.readTree(postBody);
                JsonNode result = restFluxClient.postAction(pObj);
                assertEquals("doggie", result.get("name").asText());
                assertEquals("available", result.get("status").asText());

    }


    @Test
    public void testGetAction() {
        MockResponse mockResponse = new MockResponse().addHeader("Content-Type","application/json").setBody("{\n" +
                "  \"id\": 1,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}").throttleBody(16, 5, TimeUnit.SECONDS);
        mockWebServer.enqueue(mockResponse);
        JsonNode result = restFluxClient.getById(1);
        assertEquals("doggie", result.get("name").asText());
    }
}
