package com.darrendev.restclient;

import com.darrendev.restclient.client.RestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
public class RestclientApplication {



    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RestclientApplication.class, args);
        RestClient restClient = applicationContext.getBean(RestClient.class);
        restClient.runTests(args);

    }





}
