package com.nacho.blog.querydsl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_ITEM")
public class Item {

  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @ManyToOne
  @JoinColumn(name = "f_product_id")
  private Product product;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}
