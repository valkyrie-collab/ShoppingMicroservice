package com.valkyrie.order_service.feign;

import com.valkyrie.order_service.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("INVENTORY-SERVICE")
public interface InventoryFeignController {

    @PostMapping("/inventory/update-inventory")
    ResponseEntity<String> updateInventory(@RequestBody Inventory inventory);

    @GetMapping("/inventory/stock-of-product")
    ResponseEntity<Inventory> stock(@RequestParam String productName);
}
