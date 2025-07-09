package com.valkyrie.inventory_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
    private String productName;
    private int quantity;
    
    public int getId() {return id;}
    
    public int getProductId() {return productId;}
    
    public String getProductName() {return productName;}
    
    public int getQuantity() {return quantity;}

    public Inventory setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public Inventory setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Inventory setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
