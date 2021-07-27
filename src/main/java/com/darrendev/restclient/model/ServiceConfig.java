package com.darrendev.restclient.model;

public class ServiceConfig {

    private String action;
    private String response;
    private String body;
    private String param;

    public ServiceConfig() {

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ServiceConfig{" +
                "action='" + action + '\'' +
                ", response='" + response + '\'' +
                ", body='" + body + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
