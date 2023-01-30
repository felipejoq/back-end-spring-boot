package com.uncodigo.clientes.app.models.dao;

import com.uncodigo.clientes.app.models.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IInvoiceDao extends JpaRepository<Invoice, Long> {

    @Query("select i from Invoice i where i.client.id = ?1")
    public Page<Invoice> findByClient(Long id, Pageable pageable);

}
