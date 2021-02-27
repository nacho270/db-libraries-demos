package com.nacho.blog.querydsl.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.nacho.blog.querydsl.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadDBListener {

  @Autowired
  private final ProductService productService;

  @SneakyThrows
  @EventListener(ApplicationReadyEvent.class)
  public void createDB() {
    log.info("inserting sample data");

    log.info("Created product: {}", productService.createProduct("book", BigDecimal.valueOf(10.5)));
    log.info("Created product: {}", productService.createProduct("macbook pro", BigDecimal.valueOf(3000)));
    log.info("Created product: {}", productService.createProduct("monitor", BigDecimal.valueOf(500)));
    log.info("Created product: {}", productService.createProduct("mouse", BigDecimal.valueOf(18.9)));

    log.info("finished inserting sample data");
  }
}
