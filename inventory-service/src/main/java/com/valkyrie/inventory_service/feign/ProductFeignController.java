package com.valkyrie.inventory_service.feign;

import com.valkyrie.inventory_service.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PRODUCT-SERVICE")
public interface ProductFeignController {

    @GetMapping("/product/find-product-by-name")
    ResponseEntity<Product> findProductByName(@RequestParam String name);
}
