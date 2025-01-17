package com.foro.Alura.excepcion;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ManejadorExcepciones {

    // Manejo de excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarExcepcionGeneral(Exception ex, WebRequest request) {
        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejo de excepciones específicas (por ejemplo, entidad no encontrada)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarEntidadNoEncontrada(EntityNotFoundException ex, WebRequest request) {
        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                "Recurso no encontrado",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }


    // Manejo de argumentos no válidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetalles> manejarArgumentoInvalido(IllegalArgumentException ex, WebRequest request) {
        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                "Argumento no válido: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    // Manejo de violaciones de integridad de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetalles> manejarViolacionIntegridad(DataIntegrityViolationException ex, WebRequest request) {
        ErrorDetalles errorDetalles = new ErrorDetalles(
                LocalDateTime.now(),
                "Violación de integridad de datos",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetalles, HttpStatus.CONFLICT);
    }

}

// Clase auxiliar para estructurar los errores
class ErrorDetalles {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetalles(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters y setters
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
