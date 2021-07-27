package com.darrendev.restclient.configuration;

import com.darrendev.restclient.config.PropertySourceResolver;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;


@TestConfiguration
public class ClientTestConfiguration implements WebFluxConfigurer {

    Logger logger = LoggerFactory.getLogger(ClientTestConfiguration.class);

    @Value("${basepath}")
    private String basepath;

    DockerImageName TODO_WEBAPP = DockerImageName.parse("darrenrabbitt/todo-app:v2");



    Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(8080), new ExposedPort(8080)));
    @Container
    private GenericContainer<?> todoApp = new GenericContainer<>(TODO_WEBAPP).withExposedPorts(8080).withCreateContainerCmdModifier(cmd).waitingFor(Wait.forHttp("/task").forStatusCode(200));

    @Bean
    public GenericContainer getContainer() {
        return todoApp;
    }


}

