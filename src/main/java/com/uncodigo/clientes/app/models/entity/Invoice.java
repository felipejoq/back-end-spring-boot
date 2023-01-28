package com.uncodigo.clientes.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;

    private String observation;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<ItemInvoice> items;

    public Invoice() {
        this.items = new ArrayList<>();
    }

    @PrePersist
    private void prePersist() {
        this.createAt = new Date();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<ItemInvoice> getItems() {
        return items;
    }

    public void setItems(List<ItemInvoice> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        Double totalAmount = 0.0;
        for (ItemInvoice item : this.items) {
            totalAmount += item.getToAmount();
        }
        return totalAmount;
    }
}
