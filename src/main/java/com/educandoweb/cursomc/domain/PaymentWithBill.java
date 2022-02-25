package com.educandoweb.cursomc.domain;

import com.educandoweb.cursomc.domain.enums.PaymentStatus;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PaymentWithBill extends Payment{
    private static final long serialVersionUID = 1L;

    private Date dueDate;
    private Date paymentDate;

    public PaymentWithBill(){
    }

    public PaymentWithBill(Integer id, PaymentStatus status, Purchase purchase, Date dueDate, Date paymentDate) {
        super(id, status, purchase);
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
