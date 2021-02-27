package com.nacho.blog.querydsl.repository;

import java.util.List;
import java.util.UUID;

import com.nacho.blog.querydsl.model.Product;

public interface ProductRepository {
    Product createProduct(Product product);

    List<Product> getProducts();

    Product getById(UUID id);
}
