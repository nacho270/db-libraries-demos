package com.nacho.blog.jooq.jooqdemo.repository.impl;

import static org.jooq.generated.tables.TItem.T_ITEM;
import static org.jooq.generated.tables.TShipment.T_SHIPMENT;

import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nacho.blog.jooq.jooqdemo.model.Shipment;
import com.nacho.blog.jooq.jooqdemo.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShipmentRepositoryImpl extends BasicRespository implements ShipmentRepository {

  @Autowired
  private DSLContext dslContext;

  @Autowired
  private EntityManager entityManager;

  @Override
  public Shipment getById(final UUID id) {
    return Optional.ofNullable( //
        nativeQuery(entityManager, dslContext //
            .select() //
            .from(T_SHIPMENT) //
            .innerJoin(T_ITEM).on(T_ITEM.F_SHIPMENT_ID.eq(T_SHIPMENT.ID)) //
            .where(T_SHIPMENT.ID.eq(id.toString())), Shipment.class)) //
        .map(l -> l.get(0)) //
        .orElse(null);
  }

  @Override
  public Shipment insertShipment(final Shipment shipment) {
    final UUID shipmentId = UUID.randomUUID();
    shipment.setId(shipmentId.toString());
    entityManager.persist(shipment);
    return shipment;
  }

  @Override
  public Integer getShipmentCount() {
    return dslContext.fetchCount(T_SHIPMENT);
  }

  @Override
  public void clearShipments() {
    nativeQuery(entityManager, dslContext.deleteFrom(T_ITEM));
    nativeQuery(entityManager, dslContext.deleteFrom(T_SHIPMENT));
  }
}
