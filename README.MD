# A Rest Client using Spring Webflux

This rest client is modelled against the toDo Kotlin application 
which can be found - 
https://github.com/darrenrabbs/todo-app/tree/master

## Docker run
docker pull darrenrabbitt/todo-app:v2\
docker run -it -d -p 8080:8080 darrenrabbitt/todo-app:v2

## Endpoints are as follows 

 
 | EndPoint | Action ||
 | -------   | --- | -----------------------|
 | task/     | GET |   returns all tasks      |
 | task/{id} | GET | returns a task with specific ID |
 | task      | POST | with json body will post a new task |
 | task/{id} | PUT  | with id and json body will update a task|
 | task/{id} | DELETE | with id will delete a task            |
 
 Decided to use Spring's Webflux for this impementation.
 
## What is Spring Webflux
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
 
 
 ## Tech Stack
 Java, Spring WebFlux, Junit, Lombok, Docker
 
 
## Working 
 - get by Id, get all, post, put, delete rest 
 

## Output







![!drawing =10px](output.JPG?raw=true "Output Example" )
 


## For manual testing of Rest Service
- curl -w "\n" -X POST \
  -H "Content-Type: application/json" \
  -d '{"description": "write code"}' http://localhost:8080/task
  
  curl -w "\n" -X POST \
  -H "Content-Type: application/json" \
  -d '{"description": "test"}' http://localhost:8080/task 
\
Provided the docker container is running \
  docker pull darrenrabbitt/todo-app:v2\
  docker run -it -d -p 8080:8080 darrenrabbitt/todo-app:v2\
 The rest client should run and display above output in the console\
  
## nice to haves
Security api testing - using Kotlin add Spring security to test OAuth authentication


## References
https://howtodoinjava.com/spring-webflux/webclient-get-post-example/
https://www.baeldung.com/spring-5-webclient