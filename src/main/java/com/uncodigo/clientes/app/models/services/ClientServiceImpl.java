package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.dao.IClientDao;
import com.uncodigo.clientes.app.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clientService")
public class ClientServiceImpl implements IClientServices{

    private IClientDao clientDao;

    public ClientServiceImpl(IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return this.clientDao.findAll();
    }
}
