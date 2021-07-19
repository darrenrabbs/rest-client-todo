# A Rest Client using Spring Webflux

This rest client is modelled against the toDo Kotlin application 
which can be found - 
https://github.com/darrenrabbs/todo-app/tree/master

Endpoints are as follows 
 - GET tasks/  - return a list of tasks
 - GET tasks/{id} - return a task
 - POST tasks  - add a task
 - DELETE/{id} - delete as task
 
 
 Decided to use Spring's Webflux for this impementation.
 
What is Spring Webflux
 There is  general misconception in Java is that in order to make anything concurrent, 
 one needs a large number of threads, which in reality is not the case. 
 One can achieve concurrency in Java with a few fixed number of threads by using HTTP sockets and pushing chunks of 
 data at a time through the sockets. This mechanism is called event-loop, the idea made popular by Node.js. 
 Such an approach is scalable and resilient. 
 Spring 5's spring-webflux uses the event-loop approach to provide asynchronicity.
 Additional Info:
 spring-webflux, WebFilter and WebHandler are non-blocking. 
 These methods return a Mono<void> which is a promise type in Reactor. 
 Mono<void> contains information about the completion or failure of the process.
 
 
 #Tech Stack
 Java, Spring WebFlux, Junit, Lombok,
 
 
# Working in progress
 - get is working
 - post is throwing an exception with content type..
 
 
# Features to work on
- complete all rest client testing on webflux
- unit tests with mock data
- upload the rest service as a docker container which can be used with Fabric or a maven test-containers.
- have a json file passed into the client which can then loop over all entries and give a report.


# For testing
Due to an issue with post, curl can be used to populate the in memory database
- curl -w "\n" -X POST \
  -H "Content-Type: application/json" \
  -d '{"description": "write code"}' http://localhost:8080/task
  
  curl -w "\n" -X POST \
  -H "Content-Type: application/json" \
  -d '{"description": "test"}' http://localhost:8080/task 
  
  
# nice to haves
Security api testing - using Kotlin add Spring security to test OAuth authentication


#References
https://howtodoinjava.com/spring-webflux/webclient-get-post-example/
https://www.baeldung.com/spring-5-webclient