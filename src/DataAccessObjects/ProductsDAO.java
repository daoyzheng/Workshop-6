/*
Purpose: Interface for product data access object
Author:  Dao Zheng
Date: May, 2019
 */
package DataAccessObjects;

import DomainEntities.Product;

import java.util.ArrayList;

public interface ProductsDAO {
    ArrayList<Product> getAllProducts();
    Product getProductById(int productId);
    void updateProduct(Product oldProduct, Product newProduct);
    Product addProduct(Product product);
    //  boolean deleteProduct(Product product);
}
