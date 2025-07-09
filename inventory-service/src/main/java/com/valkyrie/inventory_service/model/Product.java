package com.valkyrie.inventory_service.model;

import jakarta.persistence.*;

public class Product {
    private int id;
    private String name;
    private String type;
    private int price;
    
    public int getId() {return id;}
    
    public String getName() {return name;}
    
    public String getType() {return type;}
    
    public int getPrice() {return price;}

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setType(String type) {
        this.type = type;
        return this;
    }

    public Product setPrice(int price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
