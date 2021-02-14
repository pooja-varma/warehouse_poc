# warehouse_poca

# Code Assignment

## Intro
This assignment will be used as a discussion during a technical interview.
The primary values for the code we look for are: simplicity, readability, maintainability, testability. It should be easy to scan the code, and rather quickly understand what it’s doing. Pay attention to naming.
 
You may choose any coding language, and we look forward to discussing your choice.

## The Task
The assignment is to implement a warehouse software. This software should hold articles, and the articles should contain an identification number, a name and available stock. It should be possible to load articles into the software from a file, see the attached inventory.json.
The warehouse software should also have products, products are made of different articles. Products should have a name, price and a list of articles of which they are made from with a quantity. The products should also be loaded from a file, see the attached products.json. 
 
The warehouse should have at least the following functionality;
* Get all products and quantity of each that is an available with the current inventory
* Remove(Sell) a product and update the inventory accordingly

Run application using below steps
1. clone repository
2. build project using mvn clean install
2. run the main application

use following api sequentially

#http://localhost:8080/
Load landing page of an application

##http://localhost:8080/loadData
Read data from inventory.json and product.json and added data to inmemory db (H2 database)

##http://localhost:8080/h2-ui
use above link to check data added to db or not

##http://localhost:8080/getInventory
Load inventory details from db

##http://localhost:8080/getProducts
load product details from db

##http://localhost:8080/getAvailableProducts
load count and product which are availabe to sale

##http://localhost:8080/saleProduct?id=1 
sale selected product by specifying product id


