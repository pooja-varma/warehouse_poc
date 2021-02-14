package com.warehouse.repository;

import com.warehouse.entities.Article;
import com.warehouse.entities.ProductArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
   @Query(value= "Select a from Article a where a.article_id = :article_id")
   Article findAllByArticleID(@Param("article_id") Integer article_id);

   @Modifying(clearAutomatically = true)
   @Query(value= "Update Article a set a.stock = :stock where a.article_id = :article_id")
   int UpdateStock(@Param("stock") int stock, @Param("article_id") int article_id);

     @Query(value= "Select SUM(a.stock) from Article a")
   int countStock();

   List<Article> findAll();



}

