package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.dao.IClientDao;
import com.uncodigo.clientes.app.models.entity.Client;
import com.uncodigo.clientes.app.models.services.interfaces.IClientServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clientService")
public class ClientServiceImpl implements IClientServices {

    private IClientDao clientDao;

    public ClientServiceImpl(IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return this.clientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return clientDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findOne(Long id) {
        return this.clientDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return this.clientDao.save(client);
    }

    @Override
    @Transactional
    public Client update(Long id) {
        return null;
    }

    @Override
    @Transactional
    public Client delete(Long id) {
        Client client = this.findOne(id);
        this.clientDao.deleteById(id);
        return client;
    }
}
