package com.nacho.blog.jooq.jooqdemo.repository.impl;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nacho.blog.jooq.jooqdemo.model.Product;
import com.nacho.blog.jooq.jooqdemo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private final DSLContext dslContext;

    @Override
    public Product createProduct(final Product product) {
        final UUID productId = UUID.randomUUID();
        dslContext//
                .insertInto(table("product")) //
                .columns(field("id"), field("name"), field("price")) //
                .values(productId, product.getName(), product.getPrice()) //
                .execute();
        product.setId(productId);
        return product;
    }

    @Override
    public List<Product> getProducts() {
        return dslContext.select() //
                .from("product") //
                .fetch().stream() //
                .map(r -> Product.builder() //
                        .id(r.get("id", UUID.class)) //
                        .name(r.get("name", String.class)) //
                        .price(r.get("price", BigDecimal.class)) //
                        .build()) //
                .collect(Collectors.toList());
    }

    @Override
    public Product getById(final UUID id) {
        return dslContext.select() //
                .from("product") //
                .where(field("id", UUID.class).equal(id)) //
                .fetchOne() //
                .map(r -> Product.builder() //
                        .id(r.get("id", UUID.class)) //
                        .name(r.get("name", String.class)) //
                        .price(r.get("price", BigDecimal.class)) //
                        .build());
    }
}
