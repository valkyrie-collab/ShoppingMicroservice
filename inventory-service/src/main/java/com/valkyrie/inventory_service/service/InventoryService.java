package com.valkyrie.inventory_service.service;

import com.valkyrie.inventory_service.feign.ProductFeignController;
import com.valkyrie.inventory_service.model.Inventory;
import com.valkyrie.inventory_service.model.Product;
import com.valkyrie.inventory_service.model.Store;
import com.valkyrie.inventory_service.repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private InventoryRepo repo;
    @Autowired
    private void setRepo(InventoryRepo repo) {this.repo = repo;}

    private ProductFeignController feign;
    @Autowired
    private void setFeign(ProductFeignController feign) {this.feign = feign;}

    public Store<String> updateInventory(Inventory inventory) {
        Product product = feign.findProductByName(inventory.getProductName()).getBody();

        if (product != null) {

            if (inventory.getProductId() == product.getId()) {
                repo.save(inventory);
            } else if (inventory.getProductName().equals(product.getName())) {
                inventory.setProductId(product.getId());
                repo.save(inventory);
            }

            if (repo.findById(inventory.getId()).orElse(null) == null) {
                return Store.initialize(HttpStatus.BAD_REQUEST, "Not saved");
            } else {
                return Store.initialize(HttpStatus.OK, "saved successfully");
            }
        } else {
            return Store.initialize(
                    HttpStatus.BAD_REQUEST, "Product is not present to be saved in inventory");
        }

    }

    public Store<Inventory> getQuantity(String productName) {
        Inventory inventory = repo.findByProductName(productName);

        if (inventory == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, null);
        } else {
            return Store.initialize(HttpStatus.OK, inventory);
        }

    }
}
