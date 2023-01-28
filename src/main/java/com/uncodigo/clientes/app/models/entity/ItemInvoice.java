package com.uncodigo.clientes.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "invoice_items")
public class ItemInvoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer cuanty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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

    public Double getToAmount(){
        return this.cuanty.doubleValue() * this.product.getPrice();
    }
}
