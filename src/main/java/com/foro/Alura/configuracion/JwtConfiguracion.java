package com.foro.Alura.configuracion;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfiguracion {

    private static final Logger logger = LoggerFactory.getLogger(JwtConfiguracion.class);

    @Value("${JWT_SECRET:?La clave JWT no está configurada}")
    private String secretKey;

    /**
     * Verifica que la clave JWT esté configurada correctamente después de la inicialización del componente.
     */
    @PostConstruct
    public void verificarClave() {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            logger.error("La clave JWT no está configurada. Asegúrate de establecer la variable JWT_SECRET.");
            throw new IllegalStateException("La clave JWT no está configurada. Revisa la configuración de JWT_SECRET.");
        }
        logger.info("JWT_SECRET configurada correctamente.");
    }

    /**
     * Proporciona el objeto `SecretKeySpec` para la firma JWT.
     *
     * @return SecretKeySpec basado en la clave JWT configurada.
     */
    @Bean
    public SecretKeySpec jwtSecretKey() {
        try {
            // Intentar decodificar la clave como Base64
            byte[] decodedKey = Base64.getDecoder().decode(secretKey);
            logger.info("La clave JWT fue decodificada correctamente como Base64.");
            return new SecretKeySpec(decodedKey, "HmacSHA256");
        } catch (IllegalArgumentException e) {
            // Si no es Base64, usar la clave directamente como bytes
            logger.warn("La clave JWT no está en formato Base64. Se usará directamente como bytes.");
            return new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        }
    }
}
