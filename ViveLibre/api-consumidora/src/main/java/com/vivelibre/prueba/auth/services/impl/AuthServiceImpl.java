package com.vivelibre.prueba.auth.services.impl;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vivelibre.prueba.auth.services.AuthService;

import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService{

	private final WebClient webClient;

    public AuthServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Mono<Map<String, String>> obtenerToken() {
            
        return webClient.post()
                .uri("/token")
                .header("Content-Type", "application/json")
                .bodyValue("{ \"username\":\"auth-vivelibre\", \"password\":\"password\" }")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    String token = (String) response.get("token");
                    String timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
                    return Map.of("token", token, "timestamp", timestamp);
                });
    }
}
