package com.nacho.blog.jooq.jooqdemo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nacho.blog.jooq.jooqdemo.model.Product;
import com.nacho.blog.jooq.jooqdemo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRespository;

    public Product createProduct(final String name, final BigDecimal price) {
        return productRespository.createProduct(Product.builder() //
                .name(name) //
                .price(price) //
                .build());
    }

    public List<Product> getProducts() {
        return productRespository.getProducts();
    }

    public Product getById(final UUID id) {
        return productRespository.getById(id);
    }
}
