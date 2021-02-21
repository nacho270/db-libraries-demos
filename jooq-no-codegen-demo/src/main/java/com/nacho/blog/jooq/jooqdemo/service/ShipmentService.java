package com.nacho.blog.jooq.jooqdemo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nacho.blog.jooq.jooqdemo.controller.dto.CreateShipmentRequest.ItemRequest;
import com.nacho.blog.jooq.jooqdemo.model.Item;
import com.nacho.blog.jooq.jooqdemo.model.Product;
import com.nacho.blog.jooq.jooqdemo.model.Shipment;
import com.nacho.blog.jooq.jooqdemo.repository.ShipmentRepository;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ProductService productService;

    public Shipment getById(final UUID id) {
        return shipmentRepository.getById(id);
    }

    @Transactional
    public UUID createShipment(final List<ItemRequest> requestItems) {
        Shipment shipment = mapShipment(requestItems);

        shipment = shipmentRepository.insertShipment(shipment);

        shipmentRepository.insertShipmentItems(shipment);

        return shipment.getId();
    }

    public Integer getShipmentCount() {
        return shipmentRepository.getShipmentCount();
    }

    private Shipment mapShipment(final List<ItemRequest> requestItems) {
        final Shipment shipment = new Shipment();
        final List<Item> items = requestItems.stream() //
                .map(this::mapItem) //
                .collect(Collectors.toList());
        shipment.setItems(items);
        shipment.setTotal(items.stream()//
                .map(it -> it.getProduct().getPrice().multiply(BigDecimal.valueOf(it.getQuantity().longValue()))) //
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return shipment;
    }

    private Item mapItem(final ItemRequest ir) {
        final Product product = productService.getById(ir.getProduct());
        final Item item = Item.builder() //
                .id(UUID.randomUUID()) //
                .product(product) //
                .quantity(ir.getQuantity()) //
                .build();
        return item;
    }

}
