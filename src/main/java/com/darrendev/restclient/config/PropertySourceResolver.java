package com.darrendev.restclient.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySourceResolver {

    @Value("${baseurl}")
    private String baseurl;


    @Value("${basepath}")
    private String basepath;


    public String getBaseurl() {
        return baseurl;
    }

    public String getBasepath() {
        return basepath;
    }

}

