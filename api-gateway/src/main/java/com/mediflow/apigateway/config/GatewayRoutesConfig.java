package com.mediflow.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Every MediFlow service already owns a distinct top-level path
 * (/auth, /patients, /doctors, /appointments, /prescriptions, /bills,
 * /notifications), so routing here is a straight pass-through by path -
 * no rewriting needed. JWT enforcement itself lives in
 * {@link com.mediflow.apigateway.filter.JwtAuthGlobalFilter}, which runs
 * as a global filter on every request, so it doesn't need to be wired
 * into each route individually.
 */
@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("auth-service", r -> r
                        .path("/auth/**", "/auth")
                        .uri("lb://AUTH-SERVICE"))
                .route("patient-service", r -> r
                        .path("/patients/**", "/patients")
                        .uri("lb://PATIENT-SERVICE"))
                .route("doctor-service", r -> r
                        .path("/doctors/**", "/doctors")
                        .uri("lb://DOCTOR-SERVICE"))
                .route("appointment-service", r -> r
                        .path("/appointments/**", "/appointments")
                        .uri("lb://APPOINTMENT-SERVICE"))
                .route("prescription-service", r -> r
                        .path("/prescriptions/**", "/prescriptions")
                        .uri("lb://PRESCRIPTION-SERVICE"))
                .route("billing-service", r -> r
                        .path("/bills/**", "/bills")
                        .uri("lb://BILLING-SERVICE"))
                .route("notification-service", r -> r
                        .path("/notifications/**", "/notifications")
                        .uri("lb://NOTIFICATION-SERVICE"))
                .build();
    }
}
