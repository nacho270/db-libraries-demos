package com.nacho.blog.querydsl.repository;

import java.util.UUID;

import com.nacho.blog.querydsl.model.Shipment;

public interface ShipmentRepository {

  Shipment getById(UUID id);

  Shipment insertShipment(Shipment shipment);

  Integer getShipmentCount();

  void clearShipments();

}
