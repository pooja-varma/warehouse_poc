package com.warehouse.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Getter @Setter

public class ProductArticle {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer ID;

  private Integer article_id;
  private Integer product_id;
  private Integer amount_of;

  public ProductArticle(Integer article_id,Integer product_id,Integer amount_of){
    this.article_id=article_id;
    this.product_id=product_id;
    this.amount_of=amount_of;
  }

}
