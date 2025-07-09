package com.valkyrie.inventory_service.controller;

import com.valkyrie.inventory_service.model.Inventory;
import com.valkyrie.inventory_service.model.Store;
import com.valkyrie.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private InventoryService service;
    @Autowired
    private void setService(InventoryService service) {this.service = service;}

    @PostMapping("/update-inventory")
    public ResponseEntity<String> updateInventory(@RequestBody Inventory inventory) {
        Store<String> store = service.updateInventory(inventory);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @GetMapping("/stock-of-product")
    public ResponseEntity<Inventory> stock(@RequestParam String productName) {
        Store<Inventory> store = service.getQuantity(productName);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }
}
