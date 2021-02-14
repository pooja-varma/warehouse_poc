package com.warehouse.repository;

import com.warehouse.entities.Article;
import com.warehouse.entities.Product;
import com.warehouse.entities.ProductArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductArticleRepository extends JpaRepository<ProductArticle,Integer> {
  @Query(value= "Select pa from ProductArticle pa where pa.product_id = :product_id")
   List<ProductArticle> findAllByProductID(@Param("product_id") Integer product_id);



}