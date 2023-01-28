package com.uncodigo.clientes.app.models.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name field is required.")
    @Size(min = 3, max = 255, message = "Name field size must be between 3 and 255")
    private String name;

    @Column(name = "lastname", nullable = false)
    @NotEmpty(message = "Last name field is required.")
    @Size(min = 3, max = 255, message = "Last name field size must be between 3 and 255")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    @NotEmpty
    @Email(message = "Check Email field, this should have the email format.")
    @Size(max = 255)
    private String email;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToOne(fetch = FetchType.EAGER)
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date is not null")
    private Date createAt;

    public Client() {
        this.invoices = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        // this.createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Date getCreateAt() {
        return createAt;
    }


    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
