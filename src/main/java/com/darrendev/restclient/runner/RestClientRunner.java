package com.darrendev.restclient.runner;

import com.darrendev.restclient.client.RestFluxClient;
import com.darrendev.restclient.config.ServiceConfig;
import com.darrendev.restclient.service.FluxRestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RestClientRunner implements CommandLineRunner  {

    Logger logger = LoggerFactory.getLogger(RestClientRunner.class);

    @Autowired
    private ServiceConfig config;

    @Autowired
    private RestFluxClient restFluxClient;

    private static ObjectMapper mapper = new ObjectMapper();
    private JsonNode jsonNode;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Executing Demo requests");
        logger.info("Service config is " + config.toString());
        logger.info("Testing against " + config.getApplication());

        for(String request : config.getRequests()) {
            logger.info("Making a Rest request with " + request);
            String [] requestParts = request.split(" ",2);
            switch (requestParts[0]) {
                case "GET":
                    logger.info("Getting list request");
                    logger.info("Result: " + restFluxClient.getAll().toString());
                    break;
                case "GETBYID":
                    logger.info("Getting by ID");
                    logger.info("Result: " + restFluxClient.getById(Integer.parseInt(requestParts[1])).toString());
                    break;
                case "POST":
                    logger.info("Posting with json");
                    jsonNode = mapper.readTree(requestParts[1]);
                    logger.info("Result: " + restFluxClient.postAction(jsonNode).toString());
                    break;
                case "UPDATE":
                    logger.info("Updating with json");
                    jsonNode = mapper.readTree(requestParts[1]);
                    logger.info("Result: " + restFluxClient.updateAction(jsonNode).toString());
                    break;
                case "DELETE":
                    logger.info("Deleting request");
                    jsonNode = mapper.readTree(requestParts[1]);
                    logger.info("Result: " + restFluxClient.deleteAction(jsonNode).toString());
                    break;
                default:
                    logger.info("No Match found");
                    break;

            }
        }


    }
}
