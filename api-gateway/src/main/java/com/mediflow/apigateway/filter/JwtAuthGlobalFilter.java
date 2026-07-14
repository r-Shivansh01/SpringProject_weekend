package com.mediflow.apigateway.filter;

import com.mediflow.apigateway.dto.TokenValidationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Runs on every request that passes through the gateway. Requests under one
 * of the {@link #PROTECTED_PREFIXES} must carry a {@code Authorization:
 * Bearer <token>} header; the token is checked against auth-service's
 * {@code POST /auth/validate} endpoint before the request is forwarded on.
 *
 * Everything else (auth-service's own endpoints, the gateway's landing page,
 * actuator, static assets) passes through untouched.
 */
@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> PROTECTED_PREFIXES = List.of(
            "/patients",
            "/doctors",
            "/appointments",
            "/prescriptions",
            "/bills",
            "/notifications"
    );

    private final WebClient authServiceWebClient;
    private final String validatePath;

    public JwtAuthGlobalFilter(
            WebClient authServiceWebClient,
            @Value("${auth.validate-path}") String validatePath) {

        this.authServiceWebClient = authServiceWebClient;
        this.validatePath = validatePath;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (!isProtected(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or malformed Authorization header - expected 'Bearer <token>'");
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        return authServiceWebClient.post()
                .uri(validatePath)
                .bodyValue(Map.of("token", token))
                .retrieve()
                .bodyToMono(TokenValidationResult.class)
                .flatMap(result -> {

                    if (!result.isValid()) {
                        return unauthorized(exchange, "Invalid or expired token");
                    }

                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(builder -> builder
                                    .header("X-Auth-Email", result.getEmail())
                                    .header("X-Auth-Role", result.getRole()))
                            .build();

                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(error -> serviceUnavailable(exchange,
                        "Could not reach auth-service to validate the token: " + error.getMessage()));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isProtected(String path) {
        return PROTECTED_PREFIXES.stream().anyMatch(prefix ->
                path.equals(prefix) || path.startsWith(prefix + "/"));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        return writeJsonError(exchange, HttpStatus.UNAUTHORIZED, message);
    }

    private Mono<Void> serviceUnavailable(ServerWebExchange exchange, String message) {
        return writeJsonError(exchange, HttpStatus.SERVICE_UNAVAILABLE, message);
    }

    private Mono<Void> writeJsonError(ServerWebExchange exchange, HttpStatus status, String message) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = String.format(
                "{\"status\":%d,\"error\":\"%s\",\"message\":\"%s\"}",
                status.value(),
                status.getReasonPhrase(),
                message.replace("\"", "'")
        );

        DataBuffer buffer = response.bufferFactory()
                .wrap(body.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }
}
