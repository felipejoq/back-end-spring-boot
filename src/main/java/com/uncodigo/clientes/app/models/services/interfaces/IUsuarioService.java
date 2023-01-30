package com.uncodigo.clientes.app.models.services.interfaces;

import com.uncodigo.clientes.app.models.entity.User;

public interface IUsuarioService {
    public User findByUsername(String username);
}
