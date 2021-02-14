package com.warehouse.service;

import com.google.gson.Gson;


import com.warehouse.entities.ProductArticle;
import com.warehouse.model.Article;
import com.warehouse.model.AvailableProduct;
import com.warehouse.model.ContainArticle;
import com.warehouse.model.Product;
import com.warehouse.repository.ArticleRepository;
import com.warehouse.repository.ProductArticleRepository;
import com.warehouse.repository.ProductRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

  Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
  static int countStock;
  static List<Article> inventoryList;
  @Value("classpath:inventory.json")
  Resource inventoryFile;
  @Value("classpath:products.json")
  Resource productFile;
  @Autowired
  ArticleRepository articleRepository;
  @Autowired
  ProductRepository productRepository;
  @Autowired
  ProductArticleRepository productArticleRepository;

  @Override
  public List<Product> getAllProduct() {
    List<Product> productList = new ArrayList<>();

    try {
      List<com.warehouse.entities.Product> productArrayList = new ArrayList<>();
      productArrayList = productRepository.findAll();
      for (com.warehouse.entities.Product e : productArrayList) {
        List<ContainArticle> containArticles = new ArrayList<>();
        Product product = new Product();

        List<com.warehouse.entities.ProductArticle> productArticlesList = new ArrayList<>();
        productArticlesList = productArticleRepository.findAllByProductID(e.getProduct_id());
        logger.info("Fetch productArticle");
        for (com.warehouse.entities.ProductArticle pa : productArticlesList) {
          ContainArticle containArticle = new ContainArticle();
          containArticle.setArt_id(pa.getArticle_id());
          containArticle.setAmount_of(pa.getAmount_of());
          containArticles.add(containArticle);
        }
        product.setProduct_id(e.getProduct_id());
        product.setName(e.getProduct_name());
        product.setContain_articles(containArticles);
        productList.add(product);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return productList;
  }

  @Override
  public List<AvailableProduct> getAvailableProducts() {
    List<com.warehouse.entities.Article> inventory = articleRepository.findAll();
    List<AvailableProduct> availableProducts = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    inventoryList = new ArrayList<>();
    countStock = 0;
    for (com.warehouse.entities.Article e : inventory) {
      Article article = new Article(e.getArticle_id(), e.getArticle_name(), e.getStock());
      inventoryList.add(article);
    }
    for (Article i : inventoryList) {
      countStock += i.getStock();
    }
    while (countStock > 0) {
      products = getAllProduct();
      for (Product e : products) {
        boolean is_available=false;
        int qty = 0;
          for (ContainArticle ca : e.getContain_articles()) {
          is_available = isAvailable(ca);
          if (is_available) {
            continue;
          } else {
            is_available = false;
            break;
          }
        }
        if (is_available) {
          boolean isFound = false;
          AvailableProduct availableProduct = new AvailableProduct(e.getName(),true, qty = qty + 1);
          for (int i = 0; i < availableProducts.size(); i++) {
            AvailableProduct current = availableProducts.get(i);
            if (current.getName() == e.getName()){
              availableProduct.setQty(availableProducts.get(i).getQty()+1);
              availableProducts.set(i, availableProduct);
              isFound = true;
              break;
            }
          }
          if(!isFound) {
            availableProducts.add(availableProduct);
          }
        } else {
          break;
        }
      }
    }
    return availableProducts;
  }

  @Override
  public String saleProduct(Integer id) {
    String str=null;
    com.warehouse.entities.Product product = new com.warehouse.entities.Product();
    product = productRepository.findAllByProductID(id);
    if(product!=null) {
      List<com.warehouse.entities.ProductArticle> productArticles = new ArrayList<>();
      productArticles = productArticleRepository.findAllByProductID(id);
      for (ProductArticle pa : productArticles) {
        com.warehouse.entities.Article article = new com.warehouse.entities.Article();
        article = articleRepository.findAllByArticleID(pa.getArticle_id());
        if (article.getStock() > 0) {
          int update = articleRepository.UpdateStock(article.getStock() - pa.getAmount_of(), pa.getArticle_id());
          System.out.println(update);
        } else {
          return "Stock is not available for selected Product";
        }
      }
    }
    else
    {
      return "Please Enter valid product Id for purchase";
    }
    return " Product Name: " + product.getProduct_name() + " Sold Successfully";
  }


  private boolean isAvailable(ContainArticle ca) {
    int stock = 0;
    for (Article i : inventoryList) {
      if (i.getArt_id() == ca.getArt_id()) {
        stock = i.getStock();
        break;
      }
    }
    countStock = countStock - ca.getAmount_of();
     if ( ca.getAmount_of() <= stock) {
      for (Article i : inventoryList) {
        if (i.getArt_id() == ca.getArt_id()) {
          i.setStock( stock - ca.getAmount_of());
          System.out.println(i.toString());
          break;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public List<com.warehouse.entities.Article> getAllArticle() {
    return articleRepository.findAll();
  }

  @Override
  public void loadData() {
    //Load Inventory Data
    logger.info("Add Inventory to database");
    readInventoryData();
    logger.info("Add Product details to database");
    //Load Product Data
    readProductData();
  }


  private void readInventoryData() {
    //JSON parser object to parse read file
    Gson gson = new Gson();
    List<Article> articleList = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    try (FileReader reader = new FileReader(inventoryFile.getFile())) {
      //Read JSON file
      logger.info("Read data from inventory file");
      Object obj = jsonParser.parse(reader);
      JSONObject jsonObject = (JSONObject) obj;
      JSONArray articleArray = (JSONArray) jsonObject.get("inventory");
      for (Object e : articleArray) {
        Article article = gson.fromJson(e.toString(), Article.class);
        insertToArticle(article);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void readProductData() {
    //JSON parser object to parse read file
    Gson gson = new Gson();

    JSONParser jsonParser = new JSONParser();
    try (FileReader reader = new FileReader(productFile.getFile())) {
      //Read JSON file
      logger.info("Read Product details from product data file");
      Object obj = jsonParser.parse(reader);
      JSONObject jsonObject = (JSONObject) obj;
      JSONArray prodcutsArray = (JSONArray) jsonObject.get("products");
      Integer productID = 0;
      productArticleRepository.deleteAll();
      for (Object e : prodcutsArray) {
        Product product = gson.fromJson(e.toString(), Product.class);
        //set Product ID
        productID = productID + 1;
        product.setProduct_id(productID);

        insertToProduct(product);
        insertToProductArticle(product);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void insertToArticle(Article article) {
    com.warehouse.entities.Article article1 = new com.warehouse.entities.Article(article.getArt_id(), article.getName(), article.getStock());
    articleRepository.save(article1);
    logger.info("insert Article successfully");
  }

  private void insertToProduct(Product product) {
    com.warehouse.entities.Product newProduct = new com.warehouse.entities.Product(product.getProduct_id(), product.getName());
    productRepository.save(newProduct);
    logger.info("insert product successfully");
  }

  private void insertToProductArticle(Product product) {
    for (ContainArticle e : product.getContain_articles()) {
      ProductArticle productArticles = new ProductArticle(e.getArt_id(), product.getProduct_id(), e.getAmount_of());
      System.out.println(productArticles.toString());
      productArticleRepository.save(productArticles);
    }
    logger.info("insert to Product Article successfully");

  }

}
