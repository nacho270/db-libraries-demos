package com.nacho.blog.jooq.jooqdemo.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Shipment {

    private UUID id;
    private List<Item> items;
    private BigDecimal total;
}
