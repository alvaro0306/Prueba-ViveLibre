package com.vivelibre.api.simulada.token;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vivelibre.api.simulada.token.beans.AuthRequest;
import com.vivelibre.api.simulada.token.beans.AuthResponse;

@RestController
public class TokenController {

	@PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {
        // Validaci�n de entrada para asegurarse de que los campos no est�n vac�os
        if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required");
        }

        // Simulamos la autenticaci�n
        if ("auth-vivelibre".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
            // En caso de �xito, devolver el token simulado con tipo "Bearer"
            return ResponseEntity.ok(new AuthResponse("Bearer mock-jwt-token"));
        }

        // En caso de credenciales incorrectas, devolver un mensaje detallado
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
    
}
