package com.uncodigo.clientes.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "invoice_items")
public class ItemInvoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer cuanty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCuanty() {
        return cuanty;
    }

    public void setCuanty(Integer cuanty) {
        this.cuanty = cuanty;
    }

    public Double calculateToImport(){
        return this.cuanty.doubleValue();
    }
}
