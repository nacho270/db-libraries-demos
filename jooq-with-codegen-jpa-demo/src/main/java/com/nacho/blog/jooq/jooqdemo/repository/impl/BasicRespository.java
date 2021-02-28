package com.nacho.blog.jooq.jooqdemo.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

class BasicRespository {

  @SuppressWarnings("unchecked")
  public static <E> List<E> nativeQuery(final EntityManager em, final org.jooq.Query query, final Class<E> type) {

    // Extract the SQL statement from the jOOQ query:
    final Query result = em.createNativeQuery(query.getSQL().toUpperCase(), type);

    // Extract the bind values from the jOOQ query:
    final List<Object> values = query.getBindValues();
    for (int i = 0; i < values.size(); i++) {
      result.setParameter(i + 1, values.get(i));
    }

    // There's an unsafe cast here, but we can be sure that we'll get the right type from JPA
    return result.getResultList();
  }

  public static void nativeQuery(final EntityManager em, final org.jooq.Query query) {

    // Extract the SQL statement from the jOOQ query:
    final Query result = em.createNativeQuery(query.getSQL().toUpperCase());

    // Extract the bind values from the jOOQ query:
    final List<Object> values = query.getBindValues();
    for (int i = 0; i < values.size(); i++) {
      result.setParameter(i + 1, values.get(i));
    }
    result.executeUpdate();
  }
}
