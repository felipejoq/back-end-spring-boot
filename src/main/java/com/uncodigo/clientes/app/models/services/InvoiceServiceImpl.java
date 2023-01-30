package com.uncodigo.clientes.app.models.services;

import com.uncodigo.clientes.app.models.dao.IInvoiceDao;
import com.uncodigo.clientes.app.models.entity.Client;
import com.uncodigo.clientes.app.models.entity.Invoice;
import com.uncodigo.clientes.app.models.services.interfaces.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("invoiceService")
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private IInvoiceDao invoiceDao;

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        return this.invoiceDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Pageable pageable) {
        return this.invoiceDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Long id, Pageable pageable) {
        return this.invoiceDao.findByClient(id, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice findOne(Long id) {
        return this.invoiceDao.findById(id).orElse(null);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return this.invoiceDao.save(invoice);
    }
    @Override
    public Invoice update(Long id) {
        return null;
    }

    @Override
    public Invoice delete(Long id) {
        Invoice invoice = this.findOne(id);
        if(invoice != null) this.invoiceDao.deleteById(invoice.getId());
        return invoice;
    }

}
