package com.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@NoArgsConstructor
public class Article {
  @Id
  private Integer article_id;
  private String article_name;
  private Integer stock;


  public Article(String name){
    this.article_name = name;
  }
  public Article(Integer article_id,String article_name,Integer stock){
    this.article_id=article_id;
    this.article_name=article_name;
        this.stock=stock;
  }
}
