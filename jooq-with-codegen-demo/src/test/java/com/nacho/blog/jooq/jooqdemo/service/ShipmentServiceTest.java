package com.nacho.blog.jooq.jooqdemo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.nacho.blog.jooq.jooqdemo.controller.dto.CreateShipmentRequest.ItemRequest;
import com.nacho.blog.jooq.jooqdemo.model.Product;
import com.nacho.blog.jooq.jooqdemo.model.Shipment;
import com.nacho.blog.jooq.jooqdemo.repository.ShipmentRepository;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/init-h2.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
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
                ItemRequest.builder().product(product1.getId()).quantity(2).build(),
                ItemRequest.builder().product(product2.getId()).quantity(1).build());
        final UUID shipment = shipmentService.createShipment(items);

        // then
        final Shipment createdShipment = shipmentService.getById(shipment);
        assertThat(createdShipment).isNotNull();
        assertThat(createdShipment.getTotal()).isEqualByComparingTo(new BigDecimal(30));
        assertThat(createdShipment.getItems()).size().isEqualTo(2);
        assertThat(shipmentService.getShipmentCount()).isEqualTo(1);
    }
}
