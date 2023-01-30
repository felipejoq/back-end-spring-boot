package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.dao.IProductDao;
import com.uncodigo.clientes.app.models.entity.Product;
import com.uncodigo.clientes.app.models.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return this.productDao.findAll();
    }

    @Override
    public List<Product> findAll(String name) {
        return this.productDao.findByNameLike(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return this.productDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findOne(Long id) {
        return this.productDao.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return this.productDao.save(product);
    }

    @Override
    public Product delete(Long id) {
        Product product = this.findOne(id);
        if(product != null) this.productDao.delete(product);
        return product;
    }

    @Override
    public Product update(Long id) {
        return null;
    }
}
