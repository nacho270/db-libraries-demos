package com.nacho.blog.querydsl.repository.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nacho.blog.querydsl.model.Product;
import com.nacho.blog.querydsl.model.QProduct;
import com.nacho.blog.querydsl.repository.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

  @Autowired
  private EntityManager entityManager;

  @Override
  public Product createProduct(final Product product) {
    final UUID productId = UUID.randomUUID();
    product.setId(productId.toString());
    entityManager.persist(product);
    return product;
  }

  @Override
  public List<Product> getProducts() {
    final QProduct product = QProduct.product;
    return new JPAQueryFactory(entityManager) //
        .select(product) //
        .from(product) //
        .fetch();
  }

  @Override
  public Product getById(final UUID id) {
    final QProduct product = QProduct.product;
    return new JPAQueryFactory(entityManager) //
        .select(product) //
        .from(product) //
        .where(product.id.eq(id.toString())) //
        .fetchOne();
  }
}
