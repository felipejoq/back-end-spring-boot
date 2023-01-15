package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.entity.Client;

import java.util.List;

public interface IClientServices {
    public List<Client> findAll();

    public Client findOne(Long id);

    public Client save(Client client);

    public void delete(Long id);

    public Client update(Long id);

}
