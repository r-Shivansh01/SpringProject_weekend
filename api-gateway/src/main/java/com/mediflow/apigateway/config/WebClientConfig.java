package com.mediflow.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * Load-balanced builder - {@code http://AUTH-SERVICE/...} resolves to
     * whichever auth-service instance Eureka currently has registered,
     * instead of a hardcoded host:port.
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient authServiceWebClient(
            WebClient.Builder loadBalancedWebClientBuilder,
            @Value("${auth.service-id}") String authServiceId) {

        return loadBalancedWebClientBuilder
                .baseUrl("http://" + authServiceId)
                .build();
    }
}
