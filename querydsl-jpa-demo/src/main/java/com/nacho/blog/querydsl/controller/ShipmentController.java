package com.nacho.blog.querydsl.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nacho.blog.querydsl.controller.dto.CreateShipmentRequest;
import com.nacho.blog.querydsl.model.Shipment;
import com.nacho.blog.querydsl.service.ShipmentService;

@RestController
@RequestMapping("shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8000" })
    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Shipment> getById(@PathVariable("id") final UUID id) {
        return ResponseEntity.ok(shipmentService.getById(id));
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UUID> create(@RequestBody final CreateShipmentRequest createShipmentRequest) {
        return ResponseEntity.ok(shipmentService.createShipment(createShipmentRequest.getItems()));
    }
}
