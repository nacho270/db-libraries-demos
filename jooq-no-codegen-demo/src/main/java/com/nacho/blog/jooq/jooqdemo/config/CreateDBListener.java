package com.nacho.blog.jooq.jooqdemo.config;

import static org.jooq.impl.DSL.foreignKey;
import static org.jooq.impl.DSL.primaryKey;
import static org.jooq.impl.SQLDataType.DECIMAL;
import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.UUID;
import static org.jooq.impl.SQLDataType.VARCHAR;

import java.math.BigDecimal;

import javax.annotation.PreDestroy;

import org.jooq.DDLQuery;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.nacho.blog.jooq.jooqdemo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateDBListener {

    @Autowired
    private final DSLContext dslContext;

    @Autowired
    private ProductService productService;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void createDB() {
        log.info("Creating tables");
        createProductTable().executeAsync().thenCompose(ignored -> //
        createShipmentTable().executeAsync()).thenCompose(ignored -> //
        createItemTable().executeAsync()).thenAccept(ignored -> //
        log.info("Create tables finished")).toCompletableFuture().get();

        log.info("inserting sample data");

        productService.createProduct("book", BigDecimal.valueOf(10.5));
        productService.createProduct("macbook pro", BigDecimal.valueOf(3000));
        productService.createProduct("monitor", BigDecimal.valueOf(500));
        productService.createProduct("mouse", BigDecimal.valueOf(18.9));

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

    private DDLQuery createShipmentTable() {
        return dslContext.createTableIfNotExists("shipment") //
                .column("id", UUID.nullable(false))//
                .column("total", DECIMAL(9, 2).nullable(false)) //
                .constraints(primaryKey("id"));
    }

    private DDLQuery createItemTable() {
        return dslContext.createTableIfNotExists("item") //
                .column("id", UUID.nullable(false)) //
                .column("quantity", INTEGER.nullable(false)) //
                .column("f_product_id", UUID.nullable(false)) //
                .column("f_shipment_id", UUID.nullable(false)) //
                .constraints( //
                        primaryKey("id"), //
                        foreignKey("f_product_id").references("product", "id"), //
                        foreignKey("f_shipment_id").references("shipment", "id").onDeleteCascade() //
                );

    }

    private DDLQuery createProductTable() {
        return dslContext.createTableIfNotExists("product") //
                .column("id", UUID.nullable(false)) //
                .column("name", VARCHAR.nullable(false))//
                .column("price", DECIMAL(9, 2).nullable(false)) //
                .constraints(primaryKey("id"));
    }
}
