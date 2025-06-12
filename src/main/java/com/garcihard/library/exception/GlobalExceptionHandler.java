package com.garcihard.library.exception;

import com.garcihard.library.exception.custom.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // =====================================================================================
    // MANEJADORES PARA ERRORES DEL CLIENTE (4xx) - Logging a nivel WARN/INFO
    // =====================================================================================

    /**
     * Handler para recursos no encontrados.
     * Log a nivel WARN: Es un error del cliente, no una falla del sistema.
     * No necesitamos un stack trace para esto.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException bnfe) {

        final String errorCode = "RESOURCE_NOT_FOUND";
        log.warn("Client Error: {}. Error Code: {}", bnfe.getMessage(), errorCode);

        ErrorResponse errorResponse = ErrorResponse.of(bnfe.getMessage(), errorCode, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handler para fallas de validación en los DTOs.
     * Log a nivel WARN: El cliente envió datos basura. No es nuestro problema.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBookRequestDTO(MethodArgumentNotValidException manve) {
        final String errorCode = "VALIDATION_ERROR";
        List<FieldErrorResponse> fieldErrors = manve.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();
        /* Creamos una lista inmutable, lo cual es muy recomendable en este caso, NO SE PUEDE MODIFICAR
         * en vez de utilizar collect(Collectors.toList()) que genera una lista mutable.
         */

        log.warn("Client Error: Validation failed.",
                kv("errorCode", errorCode),
                kv("details", fieldErrors)
        );

        ErrorResponse errorResponse = ErrorResponse.of("Validation Failed", errorCode, fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // =====================================================================================
    // MANEJADOR "AGÁRRALO TODO" PARA ERRORES DEL SERVIDOR (5xx) - Logging a nivel ERROR
    // =====================================================================================

    /**
     * Handler de último recurso para cualquier excepción no capturada.
     * Esto representa un BUG en nuestro código. Debe ser un ERROR ruidoso.
     * Aquí es donde el stack trace es ORO PURO.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
        final String errorCode = "UNEXPECTED_SERVER_ERROR";
        // Genera un ID de error único para que el cliente pueda reportarlo
        // y nosotros podamos buscarlo en los logs.
        final String errorId = UUID.randomUUID().toString();

        // LOG CRÍTICO: Registra el ID del error y el stack trace completo.
        log.error("Unexpected System Error! Error ID: {}", errorId, ex);

        String userMessage = String.format("An unexpected error occurred. Please report this issue with the following ID: %s", errorId);
        ErrorResponse errorResponse = ErrorResponse.of(userMessage, errorCode, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
