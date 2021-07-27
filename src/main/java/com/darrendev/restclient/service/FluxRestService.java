package com.darrendev.restclient.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class FluxRestService implements IFluxRestService {

    private WebClient webClient;

    Logger logger = LoggerFactory.getLogger(FluxRestService.class);

    @Autowired
    public FluxRestService(WebClient client) {
        this.webClient = client;
    }

    @Value("${request.timeout.seconds}")
    private Integer timeout;

    @Override
    public Flux<JsonNode> get(String path) {
        return webClient.get().uri("/" + path)
                .retrieve()
                .bodyToFlux(JsonNode.class)
                .timeout(Duration.ofSeconds(timeout));
    }

    @Override
    public JsonNode get(String path, Integer id) {
        logger.info("Making request to " + webClient.get().uri("/" + path + "/" + id));
        return webClient.get().uri("/" + path + "/" + id)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    @Override
    public Mono<JsonNode> post(String path, JsonNode t) {
        return webClient.post().uri("/" + path)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(t), JsonNode.class)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    @Override
    public String delete(String path,Integer id) {
        return webClient.delete().uri("/" + path + "/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public Mono<JsonNode> update(String path, JsonNode t) {
        return webClient.put().uri( "/" + path + "/" + t.get("id"))
                .body(Mono.just(t), JsonNode.class)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}
