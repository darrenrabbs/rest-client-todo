package com.darrendev.restclient.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFluxRestService {
        Flux<JsonNode>  get(String path);

        JsonNode get(String path, Integer id);

        Mono<JsonNode> post(String path, JsonNode t);

        String delete(String path, Integer id);

        Mono<JsonNode> update(String path, JsonNode t);
}
