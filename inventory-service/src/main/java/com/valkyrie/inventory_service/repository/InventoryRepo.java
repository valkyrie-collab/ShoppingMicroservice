package com.valkyrie.inventory_service.repository;

import com.valkyrie.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    Inventory findByProductName(String productName);
}
