package com.uncodigo.clientes.app.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DefaultHandlerException extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> clientDataAccessException(HandlerDataAccessException e){
        Map<String, Object> response = new HashMap<>();

        response.put("ok", false);
        response.put("client", null);
        response.put("message", e.getMessage());
        response.put("error", e.getEx().getMostSpecificCause().getMessage());

        return new ResponseEntity<>(response, e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<?> clientNotFoundException(HandlerClientNotFound e) {
        Map<String, Object> response = new HashMap<>();

        response.put("ok", false);
        response.put("client", null);
        response.put("message", e.getMessage());
        response.put("error", e.getEx().getMessage());

        return new ResponseEntity<>(response, e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<?> clientValidationException(HandlerValidationException e) {
        Map<String, Object> response = new HashMap<>();

        response.put("ok", false);
        response.put("client", null);
        response.put("message", e.getMessage());
        response.put("errors", e.getAllErrors());

        return new ResponseEntity<>(response, e.getStatus());
    }
}
