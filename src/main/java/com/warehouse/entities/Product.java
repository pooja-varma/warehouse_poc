package com.warehouse.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
public class Product {

  @Id
  private Integer product_id;
  private String product_name;


  public Product(String name) {
    this.product_name = name;
  }


  public Product(Integer product_id,String name) {
    this.product_id=product_id;
    this.product_name = name;
  }

}
