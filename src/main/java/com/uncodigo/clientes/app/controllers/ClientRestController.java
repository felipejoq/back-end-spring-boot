package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.handler.exceptions.HandlerClientNotFound;
import com.uncodigo.clientes.app.handler.exceptions.HandlerDataAccessException;
import com.uncodigo.clientes.app.handler.exceptions.HandlerValidationException;
import com.uncodigo.clientes.app.models.entity.Client;
import com.uncodigo.clientes.app.models.services.IClientServices;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"}, methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api")
public class ClientRestController {

    private final Logger logger = LoggerFactory.getLogger(ClientRestController.class);

    private final IClientServices clientService;

    public ClientRestController(IClientServices clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> showAll(){
        return clientService.findAll();
    }

    @GetMapping(value = "/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable(value = "id") Long id) {

        Client client;

        try {
            client = this.clientService.findOne(id);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));

            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "Client not found!");
        }

        if(client == null) {
            logger.error("Error: ".concat("Client not found!"));

            throw new HandlerClientNotFound(new NullPointerException("Resource not found"), HttpStatus.NOT_FOUND, "Client not found!");
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Client client) {

        Client clientCreated = null;
        Map<String, Object> response = new HashMap<>();

        try {
            clientCreated = this.clientService.save(client);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));

            response.put("ok", false);
            response.put("client", clientCreated);
            response.put("message", "The client was not created, occurred an error: ".concat(e.getMessage()));
            response.put("error", e.getMostSpecificCause().getMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("ok", true);
        response.put("message", "Client created!");
        response.put("client", clientCreated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Client client, BindingResult result, @PathVariable(value = "id") Long id){


        if(result.hasErrors()) {
            throw new HandlerValidationException(HttpStatus.BAD_REQUEST, "Fields required.", result);
        }

        Client clientAfterUpdate;
        Client clientBeforeUpdate;
        Map<String, Object> response = new HashMap<>();

        try {
            clientAfterUpdate = this.clientService.findOne(id);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));

            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "The client was not updated, occurred an error.");

        }

        if(clientAfterUpdate == null) {
            logger.error("Error: Client with ID: " + id + " it's nt found or already removed!");

            throw new HandlerClientNotFound(new NullPointerException("Resource not found!"),HttpStatus.BAD_REQUEST, "Client not found!");

        }

        clientAfterUpdate.setName(client.getName());
        clientAfterUpdate.setLastName(client.getLastName());
        clientAfterUpdate.setEmail(client.getEmail());
        if(client.getCreateAt() == null) {
            clientAfterUpdate.setCreateAt(clientAfterUpdate.getCreateAt());
        } else {
            clientAfterUpdate.setCreateAt(client.getCreateAt());
        }

        try {
            clientBeforeUpdate = this.clientService.save(clientAfterUpdate);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));

            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "The client was not updated, occurred an error.");

        }

        response.put("ok", true);
        response.put("message", "Client updated!");
        response.put("client", clientBeforeUpdate);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        Map<String, Object> response = new HashMap<>();

        Client clientRemove = null;

        try {
            clientRemove = this.clientService.delete(id);
        } catch (DataAccessException e) {
            response.put("ok", false);
            response.put("client", clientRemove);
            response.put("message", "Error: Client with ID: " + id + " it's nt removed!");
            response.put("error", e.getMostSpecificCause().getMessage());
        }

        response.put("ok", true);
        response.put("message", "Client removed!");
        response.put("client", clientRemove);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
