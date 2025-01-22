package com.Soo_Shinsa.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Component
public class TossPaymentsConfig {

    @Value("${toss.client-key}")
    private String clientKey;

    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.base-url}")
    private String baseUrl;
}
