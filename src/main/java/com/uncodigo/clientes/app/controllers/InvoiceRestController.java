package com.uncodigo.clientes.app.controllers;

import com.uncodigo.clientes.app.handler.exceptions.HandlerClientNotFound;
import com.uncodigo.clientes.app.handler.exceptions.HandlerDataAccessException;
import com.uncodigo.clientes.app.handler.exceptions.HandlerValidationException;
import com.uncodigo.clientes.app.models.entity.Client;
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
    @GetMapping(value = "/clients/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> showAll() {
        return this.invoiceService.findAll();
    }

    @PermitAll
    @GetMapping(value = "/clients/invoices/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Invoice> showAllPaginate(@PathVariable Integer page) {
        return this.invoiceService.findAll(PageRequest.of(page, 10));
    }

    @PermitAll
    @GetMapping(value = "/clients/{id}/invoices/page/{page}")
    public Page<Invoice> getInvoicesByClient(@PathVariable Long id, @PathVariable Integer page) {
        return this.invoiceService.findAll(id, PageRequest.of(page, 5));
    }

    @PermitAll
    @PostMapping(value = "/clients/invoices")
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
        response.put("invoice", invoiceSave);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PermitAll
    @GetMapping(value = "/clients/invoices/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Invoice invoice = null;
        try {
            invoice = this.invoiceService.findOne(id);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));
            throw new HandlerDataAccessException(e, HttpStatus.BAD_REQUEST, "Error");
        }
        response.put("ok", true);
        response.put("message", "One Invoice founded!");
        response.put("invoice", invoice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PermitAll
    @PutMapping("/clients/invoices/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Invoice invoice, BindingResult result, @PathVariable(value = "id") Long id) {

        if (result.hasErrors()) {
            throw new HandlerValidationException(HttpStatus.BAD_REQUEST, "Fields invoice required.", result);
        }

        Invoice invoiceAfterUpdate;
        Invoice invoiceBeforeUpdate;

        Map<String, Object> response = new HashMap<>();

        try {
            invoiceAfterUpdate = this.invoiceService.findOne(id);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));
            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "The client was not updated, occurred an error (Check console log)");
        }

        if (invoiceAfterUpdate == null) {
            logger.error("Error: Client with ID: " + id + " it's nt found or already removed!");
            throw new HandlerClientNotFound(new NullPointerException("Resource not found!"), HttpStatus.BAD_REQUEST, "Invoice not found!");
        }

        invoiceAfterUpdate.setDescription(invoice.getDescription());
        invoiceAfterUpdate.setObservation(invoice.getObservation());
        invoiceAfterUpdate.setItems(invoice.getItems());

        try {
            invoiceBeforeUpdate = this.invoiceService.save(invoiceAfterUpdate);
        } catch (DataAccessException e) {
            logger.error("Error: ".concat(e.getMostSpecificCause().getMessage()));
            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "The invoice was not updated, occurred an error (Check console log)");

        }

        response.put("ok", true);
        response.put("message", "Invoice updated!");
        response.put("invoice", invoiceBeforeUpdate);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PermitAll
    @DeleteMapping("/clients/invoice/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        Map<String, Object> response = new HashMap<>();

        Invoice invoiceRemove = null;

        try {
            invoiceRemove = this.invoiceService.delete(id);
        } catch (DataAccessException e) {
            logger.error("Error: Invoice not removed. ".concat(e.getMessage()));
            throw new HandlerDataAccessException(e, HttpStatus.INTERNAL_SERVER_ERROR, "The invoice was not updated, occurred an error (Check console log)");
        }

        response.put("ok", true);
        response.put("message", "Invoice removed!");
        response.put("invoice", invoiceRemove);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
