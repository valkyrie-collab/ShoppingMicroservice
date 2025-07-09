package com.valkyrie.order_service.repository;

import com.valkyrie.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    void deleteByName(String name);

    Order findByName(String name);
}
