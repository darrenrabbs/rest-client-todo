package com.darrendev.restclient;

import com.darrendev.restclient.model.Task;
import com.darrendev.restclient.service.ITaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootTest
class RestclientApplicationTests {

    @Autowired
    ITaskService taskService;

    @Test
    void testZeroTasks() {
        Flux<Task> fluxObj = taskService.get();
        List<Task> tasks = fluxObj.collectList().block();
        assert(tasks.isEmpty());
    }

    /*
     more tests to follow using mockito
     */



}
