package com.uncodigo.clientes.app.models.dao;

import com.uncodigo.clientes.app.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientDao extends JpaRepository<Client, Long> {}
