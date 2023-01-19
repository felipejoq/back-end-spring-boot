package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClientServices {
    public List<Client> findAll();

    public Page<Client> findAll(Pageable pageable);

    public Client findOne(Long id);

    public Client save(Client client);

    public Client delete(Long id);

    public Client update(Long id);

}
