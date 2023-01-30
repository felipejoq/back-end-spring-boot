package com.uncodigo.clientes.app.models.dao;

import com.uncodigo.clientes.app.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductDao extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.name like %?1%")
    public List<Product> findByNameLike(String name);
}