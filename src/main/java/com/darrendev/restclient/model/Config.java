package com.darrendev.restclient.model;

import java.util.List;

public class Config {

    public Config() {

    }

    private List<ServiceConfig> requests;

    public Config(List<ServiceConfig> requests) {
        this.requests = requests;
    }

    public List<ServiceConfig> getRequests() {
        return requests;
    }

    public void setRequests(List<ServiceConfig> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "Config{" +
                "requests=" + requests +
                '}';
    }
}
