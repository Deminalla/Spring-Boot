package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean // otherwise, it won't find inject it constructors
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
