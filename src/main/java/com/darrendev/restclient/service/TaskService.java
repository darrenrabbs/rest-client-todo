package com.darrendev.restclient.service;

import com.darrendev.restclient.model.Task;
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
public class TaskService implements ITaskService  {


    private WebClient webClient;

    @Autowired
    public TaskService(WebClient webClient) {
        this.webClient = webClient;
    }

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
    public Mono<String> post(Task t) {
        return webClient.post().uri("/task")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(t), Task.class)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public String delete(Integer id) {
        return webClient.delete().uri("/task/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public Mono<String> update(Task t) {
        return webClient.put().uri("/task/" + t.getId())
                .body(Mono.just(t), Task.class)
                .retrieve()
                .bodyToMono(String.class);
    }
}
