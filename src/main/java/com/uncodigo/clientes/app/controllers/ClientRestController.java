package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.handler.exceptions.HandlerClientNotFound;
import com.uncodigo.clientes.app.handler.exceptions.HandlerDataAccessException;
import com.uncodigo.clientes.app.handler.exceptions.HandlerValidationException;
import com.uncodigo.clientes.app.models.entity.Client;
import com.uncodigo.clientes.app.models.services.IClientServices;
import com.uncodigo.clientes.app.models.services.IUploadPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api")
public class ClientRestController {

    private final Logger logger = LoggerFactory.getLogger(ClientRestController.class);

    private final IClientServices clientService;

    private final IUploadPhotoService uploadPhotoService;

    public ClientRestController(IClientServices clientService, IUploadPhotoService uploadPhotoService) {
        this.clientService = clientService;
        this.uploadPhotoService = uploadPhotoService;
    }

    @PermitAll
    @GetMapping(value = "/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> showAll() {
        return clientService.findAll();
    }

    @PermitAll
    @GetMapping(value = "/clients/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Client> showAllPaginate(@PathVariable Integer page) {
        // Pageable pageable = PageRequest.of(page, 10);
        return clientService.findAll(PageRequest.of(page, 10));
    }

    // @Secured({"ROLE_USER", "ROLE_SELLER", "ROLE_ADMIN"})
    // @PermitAll
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

        if (client == null) {
            logger.error("Error: ".concat("Client not found!"));

            throw new HandlerClientNotFound(new NullPointerException("Resource not found"), HttpStatus.BAD_REQUEST, "Client not found!");
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SELLER"})
    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid Client client, BindingResult result) {

        if (result.hasErrors()) {
            throw new HandlerValidationException(HttpStatus.BAD_REQUEST, "Complete fields required.", result);
        }

        Client clientCreated;
        Map<String, Object> response = new HashMap<>();

        try {
            clientCreated = this.clientService.save(client);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));

            throw new HandlerDataAccessException(e, HttpStatus.BAD_REQUEST, "Error");
        }

        response.put("ok", true);
        response.put("message", "Client created!");
        response.put("client", clientCreated);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Client client, BindingResult result, @PathVariable(value = "id") Long id) {

        if (result.hasErrors()) {
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

        if (clientAfterUpdate == null) {
            logger.error("Error: Client with ID: " + id + " it's nt found or already removed!");

            throw new HandlerClientNotFound(new NullPointerException("Resource not found!"), HttpStatus.BAD_REQUEST, "Client not found!");

        }

        clientAfterUpdate.setName(client.getName());
        clientAfterUpdate.setLastName(client.getLastName());
        clientAfterUpdate.setEmail(client.getEmail());

        if(client.getCountry() != null){
            clientAfterUpdate.setCountry(client.getCountry());
        }

        if (client.getCreateAt() == null) {
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

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        Map<String, Object> response = new HashMap<>();

        Client clientRemove = null;

        try {
            clientRemove = this.clientService.delete(id);
            if(clientRemove.getImgUrl() != null)
                this.uploadPhotoService.deleteImgFromCloudinary(clientRemove);

        } catch (DataAccessException e) {
            logger.error("Error: Client not removed. ".concat(e.getMessage()));

            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "The client was not updated, occurred an error.");
        }

        response.put("ok", true);
        response.put("message", "Client removed!");
        response.put("client", clientRemove);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/clients/photo/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("photo") MultipartFile photo, @RequestParam("id") Long id) {
        logger.info(photo.toString());
        logger.info(id.toString());

        Client client;

        try {
            client = this.clientService.findOne(id);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));

            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "Client not found!");
        }

        String url = null;
        try {
            url = this.uploadPhotoService.upload(photo, client);
            client.setImgUrl(url);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("URL DE LA FOTO: " + url);

        Client clientUpdated;
        try {
            clientUpdated = this.clientService.save(client);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));
            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "The client was not updated, occurred an error.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("message", "Photo uploaded!");
        response.put("client", clientUpdated);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
