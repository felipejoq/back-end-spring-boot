package com.uncodigo.clientes.app.models.services.interfaces;

import com.uncodigo.clientes.app.models.entity.Country;
import com.uncodigo.clientes.app.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    public List<Product> findAll();

    public List<Product> findAll(String name);

    public Page<Product> findAll(Pageable pageable);

    public Product findOne(Long id);

    public Product save(Product product);

    public Product delete(Long id);

    public Product update(Long id);
}
