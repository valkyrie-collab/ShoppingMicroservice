package com.valkyrie.order_service.service;

import com.valkyrie.order_service.feign.InventoryFeignController;
import com.valkyrie.order_service.model.Inventory;
import com.valkyrie.order_service.model.Order;
import com.valkyrie.order_service.model.OrderItem;
import com.valkyrie.order_service.model.Store;
import com.valkyrie.order_service.repository.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private static final Order defaultOrder = new Order().setOrderItems(new ArrayList<>()).setName("null");
    private OrderRepo repo;
    @Autowired
    private void setRepo(OrderRepo repo) {this.repo = repo;}

    private InventoryFeignController feign;
    @Autowired
    private void setFeign(InventoryFeignController feign) {this.feign = feign;}

    public Store<String> placeOrder(List<OrderItem> orderItems) {
        Order order = new Order().setName(UUID.randomUUID().toString()).setOrderItems(orderItems);
        repo.save(order);
        order = repo.findById(order.getId()).orElse(null);

        for(OrderItem item : orderItems) {
            Inventory inventory = feign.stock(item.getName()).getBody();

            if (inventory == null) {
//                System.out.println("bad request");
                return Store.initialize(HttpStatus.BAD_REQUEST, "There is not such product in inventory");
            } else {

                if (inventory.getQuantity() > item.getQuantity()) {
                    inventory = inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                    feign.updateInventory(inventory);
                } else {
                    return Store.initialize(HttpStatus.BAD_REQUEST,
                            "The item with name " + item.getName() + " is not present in inventory");
                }

            }

        }

        if (order != null && order.getName() != null && !order.getOrderItems().isEmpty()) {
            return Store.initialize(
                    HttpStatus.ACCEPTED,
                    "Order with Id " + order.getName() + "has placed successfully"
            );
        }

        return Store.initialize(HttpStatus.NOT_ACCEPTABLE, "Order not placed");
    }

    @Transactional
    public Store<String> cancelOrder(String name) {
        repo.deleteByName(name);

        if (repo.findByName(name) == null) {
            return Store.initialize(HttpStatus.OK, "Order cancelled successfully");
        } else {
            return Store.initialize(HttpStatus.BAD_REQUEST, "Order not cancelled");
        }

    }
}
