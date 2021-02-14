package com.warehouse.model;

import com.warehouse.entities.ProductArticle;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Setter @Getter
public class Product {
  private Integer product_id;
  private String name;
  private List<ContainArticle> contain_articles;
}
