package com.warehouse.model;

import com.warehouse.entities.ProductArticle;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Getter @Setter
public class Article {

  private Integer art_id;
  private String name;
  private Integer stock;


  public Article(Integer art_id, String name, Integer stock){
    this.art_id=art_id;
    this.name=name;
    this.stock=stock;
  }

}
