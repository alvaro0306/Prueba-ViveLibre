package com.vivelibre.prueba.auth.services;


import java.util.Map;

import reactor.core.publisher.Mono;

public interface AuthService {

	 public Mono<Map<String, String>> obtenerToken();
}
