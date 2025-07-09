package com.valkyrie.order_service.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public int getId() {return id;}

    public String getName() {return name;}

    public List<OrderItem> getOrderItems() {return orderItems;}

    public Order setName(String name) {
        this.name = name;
        return this;
    }

    public Order setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
