package com.darrendev.restclient.client;

import com.darrendev.restclient.model.Task;
import com.darrendev.restclient.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RestClient {

    /*
      was hoping to pass a json path which would contain a list of urls , actions , payloads for testing.
      for now just exercising the apis directly.
     */

    private ITaskService taskService;

    @Autowired
    public RestClient(ITaskService taskService){
        this.taskService = taskService;
    }

    public void runTests(String [] tests) {

        printAction("Post Service");
        Task nt = new Task();
        nt.setId(1L);
        nt.setDescription("cleaning");
        postTask(nt);
        printEndTest();

        printAction("Get Service with ID");
        Mono<Task> monoObj = taskService.get(1);
        Task task = monoObj.block();
        System.out.println(task.toString());
        printEndTest();

        nt = new Task();
        nt.setId(2L);
        nt.setDescription("running");
        postTask(nt);
        printEndTest();


        printAction("Get All Service");
        getListOfTasks();
        printEndTest();

        printAction("Delete Service");
        String result = taskService.delete(1);
        System.out.println(result);
        getListOfTasks();
        printEndTest();

        printAction("Put Service");
        monoObj = taskService.get(2);
        task = monoObj.block();
        task.setDescription("put description");
        Mono<String> monoObj2 = taskService.update(task);
        System.out.println("Updated value is now");
        System.out.println(monoObj2.block());
        printEndTest();


    }

    private void postTask(Task nt) {
        System.out.println("Adding " + nt.toString());
        Mono<String> monoObj2 = taskService.post(nt);
        String task2 = monoObj2.block();
        System.out.println(task2);
    }

    private void getListOfTasks() {
        Flux<Task> fluxObj;
        List<Task> tasks;
        System.out.println("Getting tasks");
        fluxObj = taskService.get();
        tasks = fluxObj.collectList().block();
        tasks.forEach(System.out::println);
    }

    private void printAction(String action) {
        System.out.println("Testing " + action);
        System.out.println("******************************************************************************************");
    }

    private void printEndTest() {
        System.out.println("*******************************************************************************************");
    }

}
