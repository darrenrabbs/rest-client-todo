package com.darrendev.restclient.client;

import com.darrendev.restclient.model.Task;
import com.darrendev.restclient.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.spec.PSource;
import java.util.List;

@Component
public class RestClient {

    /*
      was hoping to pass a json path which would contain a list of urls , actions , payloads for testing.
      for now just exercising the apis directly.
      Due do an issue with my post:
      Work around is to populate the in memory database with curl statements
     */

    @Autowired
    ITaskService taskService;

    public void runTests(String [] tests) {
        System.out.println("Testing GET");
        Flux<Task> fluxObj = taskService.get();
        List<Task> tasks = fluxObj.collectList().block();
        tasks.forEach(System.out::println);


        Mono<Task> monoObj = taskService.get(1);
        Task task = monoObj.block();
        System.out.println(task.toString());

        // BUG: broken causing an un-supported content type exception
        System.out.println("Testing POST");
        Task nt = new Task();
        nt.setId(3L);
        nt.setDescription("cleaning");

//        Mono<Task> monoObj2 = taskService.post(nt);
//        Task task2 = monoObj2.block();
//        System.out.println(task2.toString());

        System.out.println("Testing Delete");



    }



}
