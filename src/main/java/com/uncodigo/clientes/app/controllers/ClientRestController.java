package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.models.entity.Client;
import com.uncodigo.clientes.app.models.services.IClientServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"}, methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api")
public class ClientRestController {

    private final IClientServices clientService;

    public ClientRestController(IClientServices clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/clients")
    public List<Client> findAll(){
        return clientService.findAll();
    }
}
