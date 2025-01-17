package com.foro.Alura.controlador;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthControlador {

    private final SecretKeySpec jwtSecretKey;

    public AuthControlador(SecretKeySpec jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validación de credenciales específicas (esto debería ir en un servicio)
        if (validateCredentials(loginRequest.getUsername(), loginRequest.getPassword())) {
            // Añadir roles u otros claims al token
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", List.of("ROLE_USER")); // Agrega roles al token

            // Generar el token JWT
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(loginRequest.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                    .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                    .compact();

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
    }

    /**
     * Valida las credenciales del usuario.
     * Esto debería estar implementado en un servicio y consultando una base de datos.
     */
    private boolean validateCredentials(String username, String password) {
        // Ejemplo estático, reemplazar con lógica dinámica
        return "aleja".equals(username) && "123456".equals(password);
    }
}
