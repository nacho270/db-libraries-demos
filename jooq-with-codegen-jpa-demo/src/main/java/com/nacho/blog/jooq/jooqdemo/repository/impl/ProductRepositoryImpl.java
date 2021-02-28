package com.nacho.blog.jooq.jooqdemo.repository.impl;

import static org.jooq.generated.tables.TProduct.T_PRODUCT;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nacho.blog.jooq.jooqdemo.model.Product;
import com.nacho.blog.jooq.jooqdemo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl extends BasicRespository implements ProductRepository {

  @Autowired
  private final DSLContext dslContext;

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
    return nativeQuery(entityManager, //
        dslContext.select()//
            .from(T_PRODUCT),
        Product.class);
  }

  @Override
  public Product getById(final UUID id) {
    return Optional.ofNullable( //
        nativeQuery(entityManager, //
            dslContext.select()//
                .from(T_PRODUCT) //
                .where(T_PRODUCT.ID.eq(id.toString())), //
            Product.class))//
        .map(l -> l.get(0)) //
        .orElse(null);
  }
}
