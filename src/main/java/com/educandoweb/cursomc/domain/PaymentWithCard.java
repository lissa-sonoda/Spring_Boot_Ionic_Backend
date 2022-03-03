package com.educandoweb.cursomc.domain;

import com.educandoweb.cursomc.domain.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@Entity
@JsonTypeName("paymentWithCard")
public class PaymentWithCard extends Payment{
    private static final long serialVersionUID = 1L;

    private Integer installments;

    public PaymentWithCard(){
    }

    public PaymentWithCard(Integer id, PaymentStatus status, Purchase purchase, Integer installments) {
        super(id, status, purchase);
        this.installments = installments;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }
}
