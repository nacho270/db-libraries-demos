package com.nacho.blog.querydsl.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nacho.blog.querydsl.controller.dto.CreateShipmentRequest.ItemRequest;
import com.nacho.blog.querydsl.model.Product;
import com.nacho.blog.querydsl.model.Shipment;
import com.nacho.blog.querydsl.repository.ShipmentRepository;

@SpringBootTest
class ShipmentServiceTest {

  @Autowired
  private ShipmentRepository shipmentRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private ShipmentService shipmentService;

  @BeforeEach
  public void setup() {
    shipmentRepository.clearShipments();
  }

  @Test
  void testCreateShipment() {
    // given
    final Product product1 = productService.createProduct("p1", BigDecimal.TEN);
    final Product product2 = productService.createProduct("p2", BigDecimal.TEN);

    // when
    final List<ItemRequest> items = List.of( //
        ItemRequest.builder().product(UUID.fromString(product1.getId())).quantity(2).build(),
        ItemRequest.builder().product(UUID.fromString(product2.getId())).quantity(1).build());
    final UUID shipment = shipmentService.createShipment(items);

    // then
    final Shipment createdShipment = shipmentService.getById(shipment);
    assertThat(createdShipment).isNotNull();
    assertThat(createdShipment.getTotal()).isEqualByComparingTo(new BigDecimal(30));
    assertThat(createdShipment.getItems()).size().isEqualTo(2);
    assertThat(shipmentService.getShipmentCount()).isEqualTo(1);
  }
}
