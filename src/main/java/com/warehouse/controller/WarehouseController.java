package com.warehouse.controller;


import com.warehouse.entities.Article;
import com.warehouse.model.ApiError;
import com.warehouse.model.AvailableProduct;
import com.warehouse.model.Product;
import com.warehouse.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WarehouseController {

  Logger logger = LoggerFactory.getLogger(WarehouseController.class);


  @Autowired
  WarehouseService warehouseService;

  @GetMapping("/loadData")
  public ResponseEntity<String> loadData() {
    try {
      warehouseService.loadData();
      logger.info("Data Added to Database successfully");
      return new ResponseEntity<>("Data Loaded to Database", HttpStatus.OK);
    } catch (Exception e) {
      logger.error("An ERROR Message"+ e.getMessage());
      return new ResponseEntity<>("Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/getAvailableProducts")
  public ResponseEntity<List<AvailableProduct>> getAvailableProducts() {
    try {
      List<AvailableProduct> availableProducts = new ArrayList<>();
      availableProducts = warehouseService.getAvailableProducts();
      return new ResponseEntity<List<AvailableProduct>>(availableProducts, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("An ERROR Message"+ e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @GetMapping("/getProducts")
  public ResponseEntity<List<Product>> getProducts() {
    try {
      List<Product> products = new ArrayList<>();
      products = warehouseService.getAllProduct();
      return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("An ERROR Message"+ e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/saleProduct")
  public ResponseEntity<String> saleProduct(@RequestParam Integer id) {
    String str = null;
    try {
      str = warehouseService.saleProduct(id);
      logger.info("Product Sold");
      return new ResponseEntity<>(str, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("An ERROR Message"+ e.getMessage());
      return new ResponseEntity<>(str, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/getInventory")
  public ResponseEntity<List<Article>> getInventory() {
    try {
      List<Article> articles = new ArrayList<>();
      articles = warehouseService.getAllArticle();
      return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("An ERROR Message"+ e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/")
  public ResponseEntity<String> getDataDefault() {
    try {
      return new ResponseEntity<>("Welcome to warehouse Software", HttpStatus.OK);
    } catch (Exception e) {
      logger.error("An ERROR Message"+ e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
    ApiError apiError = new ApiError(
        HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }
}
