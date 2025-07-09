package com.valkyrie.product_service.controller;

import com.valkyrie.product_service.model.Product;
import com.valkyrie.product_service.model.Store;
import com.valkyrie.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;
    @Autowired
    private void setService(ProductService service) {this.service = service;}

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Product product) {
        Store<String> store = service.save(product);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody Product product) {
        return save(product);
    }

    @PostMapping("/delete-product-by-name")
    public ResponseEntity<String> deleteProductByName(@RequestParam String name) {
        Store<String> store = service.deleteProductByProductName(name);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @PostMapping("/delete-all-product-of-same-type")
    public ResponseEntity<String> deleteAllProductOfSameType(@RequestParam String type) {
        Store<String> store = service.deleteAllProductsOfSameType(type);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @GetMapping("/find-product-by-name")
    public ResponseEntity<Product> findProductByName(@RequestParam String name) {
        Store<Product> store = service.findProductByName(name);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @GetMapping("/find-all-product-by-product-type")
    public ResponseEntity<List<Product>> findAllProductByType(@RequestParam String type) {
        Store<List<Product>> store = service.findAllProductsOfSameType(type);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }
}
