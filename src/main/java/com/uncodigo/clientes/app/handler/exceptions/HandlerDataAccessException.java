package com.uncodigo.clientes.app.handler.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

public class HandlerDataAccessException extends RuntimeException{

    private String message;
    private DataAccessException ex;
    private HttpStatus status;

    public HandlerDataAccessException(DataAccessException ex, HttpStatus status, String message) {
        this.message = message;
        this.status = status;
        this.ex = ex;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataAccessException getEx() {
        return ex;
    }

    public void setEx(DataAccessException ex) {
        this.ex = ex;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
