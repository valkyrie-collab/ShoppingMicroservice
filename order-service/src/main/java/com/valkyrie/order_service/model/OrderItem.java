package com.valkyrie.order_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private int price;
    private int quantity;

    public int getId() {return id;}

    public String getName() {return name;}

    public String getType() {return type;}

    public int getPrice() {return price;}

    public int getQuantity() {return quantity;}

    public OrderItem setName(String name) {
        this.name = name;
        return this;
    }

    public OrderItem setType(String type) {
        this.type = type;
        return this;
    }

    public OrderItem setPrice(int price) {
        this.price = price;
        return this;
    }

    public OrderItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
