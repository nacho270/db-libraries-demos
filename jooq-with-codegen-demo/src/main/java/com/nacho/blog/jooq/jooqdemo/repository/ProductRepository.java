package com.nacho.blog.jooq.jooqdemo.repository;

import java.util.List;
import java.util.UUID;

import com.nacho.blog.jooq.jooqdemo.model.Product;

public interface ProductRepository {
    Product createProduct(Product product);

    List<Product> getProducts();

    Product getById(UUID id);
}
