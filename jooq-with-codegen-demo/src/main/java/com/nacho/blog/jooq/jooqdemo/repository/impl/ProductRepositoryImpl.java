package com.nacho.blog.jooq.jooqdemo.repository.impl;

import static com.nacho.blog.jooq.jooqdemo.gen.Tables.T_PRODUCT;

import java.util.List;
import java.util.UUID;

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
        dslContext.insertInto(T_PRODUCT, T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE)
                .values(productId.toString(), product.getName(), product.getPrice()) //
                .execute();
        product.setId(productId);
        return product;
    }

    @Override
    public List<Product> getProducts() {
        return dslContext.select(T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE)//
                .from(T_PRODUCT) //
                .fetchInto(Product.class);
    }

    @Override
    public Product getById(final UUID id) {
        return dslContext.select(T_PRODUCT.ID, T_PRODUCT.NAME, T_PRODUCT.PRICE) //
                .from(T_PRODUCT) //
                .where(T_PRODUCT.ID.eq(id.toString())) //
                .fetchOneInto(Product.class);
    }
}
