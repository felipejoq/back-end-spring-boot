package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.models.entity.Country;
import com.uncodigo.clientes.app.models.entity.Product;
import com.uncodigo.clientes.app.models.services.interfaces.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api")
public class ProductRestController {

    private Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    private IProductService productService;

    @GetMapping(value = "/products/{term}")
    public ResponseEntity<?> searchProductByName(@PathVariable String term) {

        logger.info("Termino recibido en el backend: ".concat(term));

        Map<String, Object> response = new HashMap<>();

        List<Product> products = this.productService.findAll(term);

        response.put("ok", true);
        response.put("products", products);
        response.put("message", "List of products by term: " + term);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
