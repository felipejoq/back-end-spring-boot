package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.handler.exceptions.HandlerDataAccessException;
import com.uncodigo.clientes.app.handler.exceptions.HandlerValidationException;
import com.uncodigo.clientes.app.models.entity.Invoice;
import com.uncodigo.clientes.app.models.services.interfaces.IInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@RestController
@RequestMapping(value = "/api")
public class InvoiceRestController {

    private final Logger logger = LoggerFactory.getLogger(InvoiceRestController.class);

    @Autowired
    private IInvoiceService invoiceService;

    @PermitAll
    @GetMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> showAll() {
        return this.invoiceService.findAll();
    }

    @PermitAll
    @GetMapping(value = "/invoices/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Invoice> showAllPaginate(@PathVariable Integer page) {
        return this.invoiceService.findAll(PageRequest.of(page, 10));
    }

    @PermitAll
    @GetMapping(value = "/clients/{id}/invoices/page/{page}")
    public Page<Invoice> getInvoicesByClient(@PathVariable Long id, @PathVariable  Integer page) {
        return this.invoiceService.findAll(id, PageRequest.of(page, 5));
    }

    @PermitAll
    @PostMapping(value = "/invoices")
    public ResponseEntity<?> create(@RequestBody @Valid Invoice invoice, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            throw new HandlerValidationException(HttpStatus.BAD_REQUEST, "Complete fields required.", result);
        }

        Invoice invoiceSave;

        try {
            invoiceSave = this.invoiceService.save(invoice);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));
            throw new HandlerDataAccessException(e, HttpStatus.BAD_REQUEST, "Error");
        }

        response.put("ok", true);
        response.put("message", "Invoice created!");
        response.put("client", invoiceSave);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
