package com.uncodigo.clientes.app.handler.exceptions;


import org.springframework.http.HttpStatus;

public class HandlerClientNotFound extends RuntimeException{

    private String message;
    private NullPointerException ex;
    private HttpStatus status;

    public HandlerClientNotFound(NullPointerException ex, HttpStatus status, String message) {
        this.message = message;
        this.ex = ex;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NullPointerException getEx() {
        return ex;
    }

    public void setEx(NullPointerException ex) {
        this.ex = ex;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
