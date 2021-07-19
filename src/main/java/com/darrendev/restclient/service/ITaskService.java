package com.darrendev.restclient.service;

import com.darrendev.restclient.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITaskService {
        Flux<Task>  get();

        Mono<Task> get(Integer id);

        Mono<Task> post(Task t);

        Mono<Void> delete(Integer id);

        Mono<Task> update(Task t);
}
