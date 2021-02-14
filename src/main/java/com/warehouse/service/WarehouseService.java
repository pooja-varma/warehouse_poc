package com.warehouse.service;



import com.warehouse.entities.Article;
import com.warehouse.model.AvailableProduct;
import com.warehouse.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WarehouseService {
  void loadData();
  List<Article> getAllArticle();
  List<Product> getAllProduct();
  List<AvailableProduct> getAvailableProducts();
  String saleProduct(Integer id);
}
