package com.nacho.blog.jooq.jooqdemo.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_SHIPMENT")
public class Shipment {

  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "f_shipment_id")
  private List<Item> items;

  @Column(name = "total", nullable = false)
  private BigDecimal total;
}
