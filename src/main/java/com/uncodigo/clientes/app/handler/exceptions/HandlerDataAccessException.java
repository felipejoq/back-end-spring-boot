package com.uncodigo.clientes.app.handler.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatusCode;

public class HandlerDataAccessException extends RuntimeException{

    private String message;
    private DataAccessException ex;
    private HttpStatusCode status;

    public HandlerDataAccessException(DataAccessException ex, HttpStatusCode status, String message) {
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

    public HttpStatusCode getStatus() {
        return status;
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }
}
