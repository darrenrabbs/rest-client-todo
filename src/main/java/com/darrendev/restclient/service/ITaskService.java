package com.darrendev.restclient.service;

import com.darrendev.restclient.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITaskService {
        Flux<Task>  get();

        Mono<Task> get(Integer id);

        Mono<String> post(Task t);

        String delete(Integer id);

        Mono<String> update(Task t);
}
