package com.nacho.blog.querydsl.controller.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class CreateProductRequest {

    private String name;
    private BigDecimal price;
}
