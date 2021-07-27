package com.darrendev.restclient.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ConfigResolver {


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
