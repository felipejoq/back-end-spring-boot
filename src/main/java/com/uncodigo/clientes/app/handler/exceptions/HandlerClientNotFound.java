package com.uncodigo.clientes.app.handler.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatusCode;

public class HandlerClientNotFound extends RuntimeException{

    private String message;
    private NullPointerException ex;
    private HttpStatusCode status;

    public HandlerClientNotFound(NullPointerException ex, HttpStatusCode status, String message) {
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

    public HttpStatusCode getStatus() {
        return status;
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }
}
