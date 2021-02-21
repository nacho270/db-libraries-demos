package com.nacho.blog.jooq.jooqdemo.repository.impl;

import static com.nacho.blog.jooq.jooqdemo.gen.Tables.T_ITEM;
import static com.nacho.blog.jooq.jooqdemo.gen.Tables.T_PRODUCT;
import static com.nacho.blog.jooq.jooqdemo.gen.Tables.T_SHIPMENT;

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
        // here there's got to be simpler way.
        // it's easier than witout codegen in the sense that aliases are not needed... but still the painful mapping
        final var shipment = new Shipment();
        shipment.setItems(new ArrayList<>());
        dslContext.select() //
                .from(T_SHIPMENT) //
                .innerJoin(T_ITEM).on(T_ITEM.F_SHIPMENT_ID.eq(T_SHIPMENT.ID)) //
                .innerJoin(T_PRODUCT).on(T_ITEM.F_PRODUCT_ID.eq(T_PRODUCT.ID)) //
                .where(T_SHIPMENT.ID.endsWith(id.toString())) //
                .fetch().stream() //
                .forEach(r -> {
                    shipment.setId(UUID.fromString(r.get(T_SHIPMENT.ID)));
                    shipment.setTotal(r.get(T_SHIPMENT.TOTAL));
                    shipment.getItems().add( //
                            Item.builder() //
                                    .id(UUID.fromString(r.get(T_ITEM.ID))) //
                                    .product(productRespository.getById(UUID.fromString(r.get(T_PRODUCT.ID)))) //
                                    .quantity(r.get(T_ITEM.QUANTITY)) //
                                    .build()); //
                });
        return shipment;
    }

    @Override
    public Shipment insertShipment(final Shipment shipment) {
        final UUID shipmentId = UUID.randomUUID();
        dslContext.insertInto(T_SHIPMENT, T_SHIPMENT.ID, T_SHIPMENT.TOTAL) //
                .values(shipmentId.toString(), shipment.getTotal()) //
                .execute();
        shipment.setId(shipmentId);
        return shipment;
    }

    @Override
    public void insertShipmentItems(final Shipment shipment) {
        shipment.getItems().forEach(it -> {
            dslContext.insertInto(T_ITEM, T_ITEM.ID, T_ITEM.QUANTITY, T_ITEM.F_PRODUCT_ID, T_ITEM.F_SHIPMENT_ID)
                    .values(UUID.randomUUID().toString(), it.getQuantity(), it.getProduct().getId().toString(), shipment.getId().toString()) //
                    .execute();
        });
    }

    @Override
    public Integer getShipmentCount() {
        return dslContext.fetchCount(T_SHIPMENT);
    }

    @Override
    public void clearShipments() {
        dslContext.deleteFrom(T_SHIPMENT).execute();
    }
}
