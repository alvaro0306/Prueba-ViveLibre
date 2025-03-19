package com.vivelibre.prueba.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivelibre.prueba.auth.services.AuthService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthService authService;

    @GetMapping("/token")
    public Mono<Map<String, String>> obtenerToken() {
        return authService.obtenerToken();
    }
}
