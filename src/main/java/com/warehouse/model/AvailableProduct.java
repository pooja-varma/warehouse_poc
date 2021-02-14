package com.warehouse.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailableProduct {
  private String name;
  private boolean isAvailable;
  private Integer qty;

  public AvailableProduct(String name, boolean isAvailable,Integer qty){
    this.name=name;
    this.isAvailable=isAvailable;
    this.qty=qty;
  }
}
