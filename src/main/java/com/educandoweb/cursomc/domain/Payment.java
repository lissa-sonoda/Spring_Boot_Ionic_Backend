package com.educandoweb.cursomc.domain;

import com.educandoweb.cursomc.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private Integer status;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "purchase_id")
    @MapsId
    private Purchase purchase;

    public Payment(){
    }

    public Payment(Integer id, PaymentStatus status, Purchase purchase) {
        this.id = id;
        this.status = (status==null) ? null : status.getCod();
        this.purchase = purchase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return PaymentStatus.toEnum(status);
    }

    public void setStatus(PaymentStatus status) {
        this.status = status.getCod();
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return id != null ? id.equals(payment.id) : payment.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
