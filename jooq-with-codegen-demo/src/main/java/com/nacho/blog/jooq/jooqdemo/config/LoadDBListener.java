package com.nacho.blog.jooq.jooqdemo.config;

import java.math.BigDecimal;

import javax.annotation.PreDestroy;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.nacho.blog.jooq.jooqdemo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!test")
public class LoadDBListener {

    @Autowired
    private final DSLContext dslContext;

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

    @PreDestroy
    public void shutshown() {
        log.warn("Deleting tables...");
        dslContext.dropTable("item").execute();
        dslContext.dropTable("shipment").execute();
        dslContext.dropTable("product").execute();
        log.warn("Finished deleting tables...");
    }
}
