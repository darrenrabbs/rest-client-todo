package com.darrendev.restclient.client;

import com.darrendev.restclient.service.IFluxRestService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RestFluxClient {


    private IFluxRestService taskService;

    @Value("${basepath}")
    private String basePath;

    @Autowired
    public RestFluxClient(IFluxRestService taskService){
        this.taskService = taskService;
    }

    Logger logger = LoggerFactory.getLogger(RestFluxClient.class);

    public List<JsonNode> getAll() {
        logger.info("GetALL");
        Flux<JsonNode> fluxObj = taskService.get(basePath);
        return fluxObj.collectList().block();
    }


    public JsonNode getById(int id) {
        logger.info("Get Item with id " + id);
        return taskService.get(basePath,id);
    }

    public JsonNode postAction(JsonNode nt) {
        logger.info("Posting new Item with description " + nt.get("description"));
        Mono<JsonNode> monoObj2 = taskService.post(basePath,nt);
        return monoObj2.block();
    }

    public JsonNode updateAction(JsonNode nt) {
        logger.info("Updating existing task " + nt.get("id") + " with description " + nt.get("description"));
        Mono<JsonNode> monoObj = taskService.update(basePath,nt);
        return monoObj.block();
    }

    public String deleteAction(JsonNode nt) {
        logger.info("Deleting existing item " + nt.get("id"));
        return taskService.delete(basePath,nt.get("id").intValue());
    }

}
