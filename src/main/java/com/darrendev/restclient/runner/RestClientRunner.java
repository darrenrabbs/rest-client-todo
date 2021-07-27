package com.darrendev.restclient.runner;

import com.darrendev.restclient.client.RestFluxClient;
import com.darrendev.restclient.config.PropertySourceResolver;
import com.darrendev.restclient.model.Config;
import com.darrendev.restclient.model.ServiceConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@ConditionalOnProperty(
        prefix = "application.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class RestClientRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(RestClientRunner.class);

    @Autowired
    private RestFluxClient restFluxClient;

    @Autowired
    PropertySourceResolver propertySourceResolver;



    private static ObjectMapper mapper = new ObjectMapper();
    private JsonNode jsonNode;


    private boolean validateResult(ServiceConfig sc, String result) {
        logger.info("Result of " + sc.getAction() + " is " + result);

        if(!result.contains(sc.getResponse())) {
            logger.warn("Expected response not received");
            return false;
        }
        return true;
    }


    private Config getConfiguration() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(propertySourceResolver.getServiceConfig()).getFile());

        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        Config config = om.readValue(file, Config.class);
        return config;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Executing Demo requests");

        Config config = getConfiguration();
        int requestCount = config.getRequests().size();
        int passCount = requestCount;
        for(ServiceConfig sc : config.getRequests()) {
            logger.info("Making a Rest request with " + sc.getAction());
            String result = null;
            switch (sc.getAction()) {
                case "GET":
                    logger.info("Getting list request");
                    result = restFluxClient.getAll().toString();
                    passCount = validateResult(sc, result) ? passCount : --passCount;
                    break;
                case "GETBYID":
                    logger.info("Getting by ID");
                    result = restFluxClient.getById(Integer.parseInt(sc.getParam())).toString();
                    passCount = validateResult(sc, result) ? passCount : --passCount;
                    break;
                case "POST":
                    logger.info("Posting with json");
                    jsonNode = mapper.readTree(sc.getBody());
                    result =  restFluxClient.postAction(jsonNode).toString();
                    passCount = validateResult(sc, result) ? passCount : --passCount;
                    break;
                case "UPDATE":
                    logger.info("Updating with json");
                    jsonNode = mapper.readTree(sc.getBody());
                    result =  restFluxClient.updateAction(jsonNode).toString();
                    passCount = validateResult(sc, result) ? passCount : --passCount;
                    break;
                case "DELETE":
                    logger.info("Deleting request");
                    jsonNode = mapper.readTree(sc.getBody());
                    result = restFluxClient.deleteAction(jsonNode).toString();
                    passCount = validateResult(sc, result) ? passCount : --passCount;
                    break;
                default:
                    logger.info("No Match found");
                    break;

            }
        }

        logger.info("Total pass count is " + (float) passCount/requestCount * 100 + "%");
    }
}
