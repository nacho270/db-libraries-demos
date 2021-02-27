package com.nacho.blog.querydsl.repository.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nacho.blog.querydsl.model.QItem;
import com.nacho.blog.querydsl.model.QShipment;
import com.nacho.blog.querydsl.model.Shipment;
import com.nacho.blog.querydsl.repository.ShipmentRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShipmentRepositoryImpl implements ShipmentRepository {

  @Autowired
  private EntityManager entityManager;

  @Override
  public Shipment getById(final UUID id) {
    final QShipment shipment = QShipment.shipment;
    final QItem item = QItem.item;
    return (Shipment) new JPAQueryFactory(entityManager) //
        .from(shipment) //
        .innerJoin(shipment.items, item).fetchJoin() //
        .where(shipment.id.eq(id.toString())) //
        .fetchOne();

  }

  @Override
  @Transactional
  public Shipment insertShipment(final Shipment shipment) {
    final UUID shipmentId = UUID.randomUUID();
    shipment.setId(shipmentId.toString());
    entityManager.persist(shipment);
    return shipment;
  }

  @Override
  public Integer getShipmentCount() {
    final QShipment shipment = QShipment.shipment;
    return new JPAQueryFactory(entityManager) //
        .select(shipment.count()) //
        .from(shipment) //
        .fetchOne() //
        .intValue();
  }

  @Transactional
  @Override
  public void clearShipments() {
    new JPAQueryFactory(entityManager).delete(QShipment.shipment).execute();
  }
}
