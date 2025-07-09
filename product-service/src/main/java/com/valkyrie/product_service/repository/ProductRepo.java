package com.valkyrie.product_service.repository;

import com.valkyrie.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByName(String name);

    List<Product> findAllByType(String type);
}
