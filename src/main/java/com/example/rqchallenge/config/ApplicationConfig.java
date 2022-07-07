package com.example.rqchallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Model representation of application.properties
 */
@Configuration
public class ApplicationConfig {

    @Value("${rest.endpoint}")
    private String restEndPoint;

    public String getRestEndPoint() {
        return restEndPoint;
    }

    public void setRestEndPoint(String restEndPoint) {
        this.restEndPoint = restEndPoint;
    }
}
