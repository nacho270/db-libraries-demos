package com.nacho.blog.jooq.jooqdemo.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private UUID id;
    private String name;
    private BigDecimal price;
}
