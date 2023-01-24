package com.uncodigo.clientes.app.handler.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerValidationException extends RuntimeException {

    private String message;
    private HttpStatus status;
    private BindingResult result;

    public HandlerValidationException(HttpStatus status, String message, BindingResult erros) {
        this.status = status;
        this.message = message;
        this.result = erros;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public BindingResult getResult() {
        return result;
    }

    public void setResult(BindingResult result) {
        this.result = result;
    }

    public List<String> getAllErrors() {
        /* List<String> errors = new ArrayList<>();
        for (FieldError property : this.result.getFieldErrors()) {
            errors.add(property.getField() + " " + property.getDefaultMessage());
        } */
        return this.result.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
