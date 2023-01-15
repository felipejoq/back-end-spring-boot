package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.models.entity.Client;
import com.uncodigo.clientes.app.models.services.IClientServices;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<Client> showAll(){
        return clientService.findAll();
    }

    @GetMapping(value = "/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Client show(@PathVariable(value = "id") Long id) {
        return this.clientService.findOne(id);
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return this.clientService.save(client);
    }

    @PutMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Client update(@RequestBody Client client, @PathVariable(value = "id") Long id){
        Client clientAfterUpdate = this.clientService.findOne(id);

        clientAfterUpdate.setName(client.getName());
        clientAfterUpdate.setLastname(client.getLastname());
        clientAfterUpdate.setEmail(client.getEmail());

        return this.clientService.save(clientAfterUpdate);
    }

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        this.clientService.delete(id);
    }
}
