package com.valkyrie.order_service.controller;

import com.valkyrie.order_service.model.OrderItem;
import com.valkyrie.order_service.model.Store;
import com.valkyrie.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService service;
    @Autowired
    private void setService(OrderService service) {this.service = service;}

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody List<OrderItem> orderItems) {
        Store<String> store = service.placeOrder(orderItems);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<String> cancelOrder(@RequestParam String name) {
        Store<String> store = service.cancelOrder(name);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }
}
