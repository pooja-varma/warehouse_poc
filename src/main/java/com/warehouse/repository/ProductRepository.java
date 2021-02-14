package com.warehouse.repository;

import com.warehouse.entities.Article;
import com.warehouse.entities.Product;
import com.warehouse.entities.ProductArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
  List<Product> findAll();

  @Query(value= "Select a from Product a where a.product_id = :product_id")
  Product findAllByProductID(@Param("product_id") Integer product_id);

}
