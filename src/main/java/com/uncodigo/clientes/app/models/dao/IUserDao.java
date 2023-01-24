package com.uncodigo.clientes.app.models.dao;

import com.uncodigo.clientes.app.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDao extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    @Query("select u from User u where u.username =?1")
    public User findByUsernameQuery(String username);
}
