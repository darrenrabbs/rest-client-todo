package com.darrendev.restclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Configuration
@EnableWebFlux
public class WebFluxConfig  implements WebFluxConfigurer {

    Logger logger = LoggerFactory.getLogger(WebFluxConfig.class);


    @Value("${baseurl}")
    private String baseurl;

    @Bean
    public WebClient getWebClient() {
        logger.info("Returning webclient for baseurl " + baseurl);
        return WebClient.builder()
                .baseUrl(baseurl)
                .defaultCookie("cookieKey","cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", baseurl))
                .build();


    }
}
