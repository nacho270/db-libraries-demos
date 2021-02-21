package com.nacho.blog.jooq.jooqdemo.repository.impl;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nacho.blog.jooq.jooqdemo.model.Item;
import com.nacho.blog.jooq.jooqdemo.model.Shipment;
import com.nacho.blog.jooq.jooqdemo.repository.ProductRepository;
import com.nacho.blog.jooq.jooqdemo.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShipmentRepositoryImpl implements ShipmentRepository {

    @Autowired
    private DSLContext dslContext;

    @Autowired
    private ProductRepository productRespository;

    @Override
    public Shipment getById(final UUID id) {
        final var shipment = new Shipment();
        shipment.setItems(new ArrayList<>());
        // painful mapping... specially when all the tables have a column with the same name
        dslContext //
                .select(field("shipment.id").as("shipment_id"), //
                        field("item.id").as("item_id"), //
                        field("product.id").as("product_id"), //
                        field("total"), //
                        field("quantity"), //
                        field("price") //
                ) //
                .from("shipment") //
                .join("item").on(field("shipment.id").eq(field("item.f_shipment_id"))) //
                .join("product").on(field("product.id").eq(field("item.f_product_id"))) //
                .where(field("shipment.id", UUID.class).equal(id)) //
                .fetch().stream() //
                .forEach(r -> {
                    // for some reason fails to parse the string/UUID to UUID when joining
                    shipment.setId(UUID.fromString(r.get("shipment_id", String.class)));
                    shipment.setTotal(r.get("total", BigDecimal.class));
                    shipment.getItems().add( //
                            Item.builder() //
                                    .id(UUID.fromString(r.get("item_id", String.class))) //
                                    .product(productRespository.getById(UUID.fromString(r.get("product_id", String.class)))) //
                                    .quantity(r.get("quantity", Integer.class)) //
                                    .build()); //
                });
        return shipment;
    }

    @Override
    public Shipment insertShipment(final Shipment shipment) {
        final UUID shipmentId = UUID.randomUUID();
        dslContext //
                .insertInto(table("shipment")) //
                .columns(field("id"), field("total")) //
                .values(shipmentId, shipment.getTotal()) //
                .execute();
        shipment.setId(shipmentId);
        return shipment;
    }

    @Override
    public void insertShipmentItems(final Shipment shipment) {
        shipment.getItems().forEach(it -> {
            dslContext //
                    .insertInto(table("item")) //
                    .columns(field("id"), field("quantity"), field("f_product_id"), field("f_shipment_id")) //
                    .values(UUID.randomUUID(), it.getQuantity(), it.getProduct().getId(), shipment.getId()) //
                    .execute();
        });
    }

    @Override
    public Integer getShipmentCount() {
        return dslContext.fetchCount(table("shipment"));
    }

    @Override
    public void clearShipments() {
        dslContext.deleteFrom(table("shipment")).execute();
    }
}
