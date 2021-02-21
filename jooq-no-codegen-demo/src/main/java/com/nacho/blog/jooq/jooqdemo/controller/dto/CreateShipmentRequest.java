package com.nacho.blog.jooq.jooqdemo.controller.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateShipmentRequest {

    private List<ItemRequest> items;

    @Data
    @Builder
    public static class ItemRequest {
        private UUID product;
        private Integer quantity;
    }
}
