package com.Soo_Shinsa.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${toss.base-url}")
    private String baseUrl;

    @Value("${toss.secret-key}")
    private String secretKey;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}