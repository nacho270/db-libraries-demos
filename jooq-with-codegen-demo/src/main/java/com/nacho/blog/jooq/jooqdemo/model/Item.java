package com.nacho.blog.jooq.jooqdemo.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Item {

    private UUID id;
    private Product product;
    private Integer quantity;
}
