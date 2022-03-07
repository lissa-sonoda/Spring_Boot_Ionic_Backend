package com.educandoweb.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
public class PurchaseItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private PurchaseItemPK id = new PurchaseItemPK();

    private Double discount;
    private Integer quantity;
    private Double price;

    public PurchaseItem(){
    }

    public PurchaseItem(Purchase purchase, Product product, Double discount, Integer quantity, Double price) {
        id.setPurchase(purchase);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
    }

    public double getSubtotal(){
        return (price - discount) * quantity;
    }

    @JsonIgnore
    public Purchase getPurchase(){
        return id.getPurchase();
    }

    public void setPurchase(Purchase purchase){
        id.setPurchase(purchase);
    }

    public Product getProduct(){
        return id.getProduct();
    }

    public void setProduct(Product product){
        id.setProduct(product);
    }

    public PurchaseItemPK getId() {
        return id;
    }

    public void setId(PurchaseItemPK id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseItem that = (PurchaseItem) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        final StringBuilder sb = new StringBuilder();
        sb.append(getProduct().getName());
        sb.append(", Quantity: ");
        sb.append(getQuantity());
        sb.append(", Unit Price: ");
        sb.append(nf.format(getPrice()));
        sb.append(", Subtotal: ");
        sb.append(nf.format(getSubtotal()));
        sb.append("\n");
        return sb.toString();
    }
}
