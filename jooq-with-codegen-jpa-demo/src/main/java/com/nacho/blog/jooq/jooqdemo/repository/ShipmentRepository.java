package com.nacho.blog.jooq.jooqdemo.repository;

import java.util.UUID;

import com.nacho.blog.jooq.jooqdemo.model.Shipment;

public interface ShipmentRepository {

  Shipment getById(UUID id);

  Shipment insertShipment(Shipment shipment);

  Integer getShipmentCount();

  void clearShipments();

}
