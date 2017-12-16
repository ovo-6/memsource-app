package com.ovo6.memsource;

/**
 * Main entry point for Spring Boot Application.
 *
 * exclude = RepositoryRestMvcAutoConfiguration.class is because we don't want
 * rest endpoints for repository objects.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class Application extends RepositoryRestConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


}
