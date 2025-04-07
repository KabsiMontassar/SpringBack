package o.springback.dto;

import lombok.Getter;
import lombok.Setter;
import o.springback.entities.GestionProduits.Products;

import java.util.Map;


public class OrderProductDto {
    private Products products;
    private Integer quantity;

    public Products getProduct() {
        return products;
    }

    public void setProduct(Products products) {
        this.products = products;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Getters/Setters
}