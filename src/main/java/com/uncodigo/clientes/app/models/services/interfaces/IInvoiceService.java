package com.uncodigo.clientes.app.models.services.interfaces;

import com.uncodigo.clientes.app.models.entity.Client;
import com.uncodigo.clientes.app.models.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IInvoiceService {
    public List<Invoice> findAll();

    public Page<Invoice> findAll(Pageable pageable);
    public Page<Invoice> findAll(Long id, Pageable pageable);

    public Invoice findOne(Long id);

    public Invoice save(Invoice invoice);

    public Invoice delete(Long id);

    public Invoice update(Long id);
}
