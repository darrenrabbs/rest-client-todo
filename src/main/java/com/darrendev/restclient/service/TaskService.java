package com.darrendev.restclient.service;

import com.darrendev.restclient.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class TaskService implements ITaskService  {

    @Autowired
    WebClient webClient;

    @Value("${request.timeout.seconds}")
    private Integer timeout;

    @Override
    public Flux<Task> get() {
        return webClient.get().uri("/task")
                .retrieve()
                .bodyToFlux(Task.class)
                .timeout(Duration.ofSeconds(timeout));
    }

    @Override
    public Mono<Task> get(Integer id) {
        return webClient.get().uri("/task/" + id)
                .retrieve()
                .bodyToMono(Task.class)
                .timeout(Duration.ofSeconds(timeout));
    }

    @Override
    public Mono<Task> post(Task t) {
        return webClient.post().uri("/task")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(t), Task.class)
                .retrieve()
                .bodyToMono(Task.class);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return null;
    }

    @Override
    public Mono<Task> update(Task t) {
        return null;
    }
}
