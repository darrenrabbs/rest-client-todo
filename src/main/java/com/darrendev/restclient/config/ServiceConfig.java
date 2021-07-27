package com.darrendev.restclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("demo")
public class ServiceConfig {

    private String application;
    private List<String> requests = new ArrayList<>();

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public List<String> getRequests() {
        return requests;
    }

    public void setRequests(List<String> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "ServiceConfig{" +
                "application='" + application + '\'' +
                ", requests=" + requests +
                '}';
    }
}
