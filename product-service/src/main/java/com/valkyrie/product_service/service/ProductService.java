package com.valkyrie.product_service.service;

import com.valkyrie.product_service.model.Product;
import com.valkyrie.product_service.model.Store;
import com.valkyrie.product_service.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private static final Product defaultProduct = new Product().setName("null").setPrice(-1).setType("null");
    private ProductRepo repo;
    @Autowired
    private void setRepo(ProductRepo repo) {this.repo = repo;}

    private boolean deleteById(int id) {
        repo.deleteById(id);
        Product product = repo.findById(id).orElse(null);
        return product == null;
    }

    public Store<String> save(Product product) {
        Product present = repo.findByName(product.getName());

        if (present == null) {

            if (product.getPrice() > 0 && product.getName() != null &&
                    product.getType() != null) {
                repo.save(product);
                present = repo.findById(product.getId()).orElse(null);
                return present == null?
                        Store.initialize(HttpStatus.NOT_ACCEPTABLE, "Product is not saved") :
                        Store.initialize(HttpStatus.ACCEPTED, "Product is saved");
            } else {
                return Store.initialize(
                        HttpStatus.NOT_ACCEPTABLE,
                        "Product is saved typically due to presence of null value");
            }

        } else if (!present.toString().equals(product.toString())) {
            boolean deleted = deleteById(present.getId());

            if (deleted) {
                repo.save(product);
                present = repo.findById(product.getId()).orElse(null);
                return present == null?
                        Store.initialize(HttpStatus.NOT_MODIFIED, "Update is successful") :
                        Store.initialize(HttpStatus.ACCEPTED, "Product has been Updated");
            } else {
                return Store.initialize(HttpStatus.NOT_MODIFIED, "Update is not successful");
            }

        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Product not saved");
    }

    public Store<List<Product>> findAllProductsOfSameType(String type) {
        List<Product> products = repo.findAllByType(type);

        if (products.isEmpty()) {
            return Store.initialize(HttpStatus.BAD_REQUEST, List.of(defaultProduct));
        } else {
            return Store.initialize(HttpStatus.OK, products);
        }

    }

    public Store<Product> findProductByName(String name) {
        Product product = repo.findByName(name);

        if (product == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, defaultProduct);
        } else {
            return Store.initialize(HttpStatus.OK, product);
        }

    }

    public Store<String> deleteProductByProductName(String name) {
        Product product = repo.findByName(name);

        if (product == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST,
                    "Product deletion Unsuccessful; There is no such product with name " + name);
        } else {
            boolean deleted = deleteById(product.getId());

            if (deleted) {
                return Store.initialize(HttpStatus.OK, "Product deleted Successfully");
            } else {
                return Store.initialize(HttpStatus.NOT_MODIFIED,
                        "Product deletion Unsuccessful; There is no such product with name " + name);
            }
        }

    }

    public Store<String> deleteAllProductsOfSameType(String type) {
        List<Product> products = repo.findAllByType(type);

        if (products.isEmpty()) {
            return Store.initialize(
                    HttpStatus.BAD_REQUEST, "There is no such product with type " + type);
        } else {
            List<Integer> productDeletionUnsuccessful = new ArrayList<>();

            for (Product product : products) {

                if (!deleteById(product.getId())) {
                    productDeletionUnsuccessful.add(product.getId());
                }

            }

            return productDeletionUnsuccessful.isEmpty()?
                    Store.initialize(HttpStatus.OK,
                            "All product with type " + type + " has been deleted successfully") :
                    Store.initialize(HttpStatus.BAD_REQUEST,
                            "The Products with ids " + productDeletionUnsuccessful + " has been deleted");
        }

    }

}
