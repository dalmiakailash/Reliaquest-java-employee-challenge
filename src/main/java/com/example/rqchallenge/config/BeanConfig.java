package com.example.rqchallenge.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public WebClient webClient(@Autowired @NonNull final ApplicationConfig applicationConfig){
        return WebClient.create(applicationConfig.getRestEndPoint());
    }

}
