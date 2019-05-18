/*
Purpose: Data Access Class for products
Author:  Dao Zheng
Date: May, 2019
 */

package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductManager {

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> productArrayList = new ArrayList<>();
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM products";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Execute Statement
            ResultSet resultSet = stmt.executeQuery();
            // Populate supplier array list
            while (resultSet.next()) {
                productArrayList.add(new Product(
                        resultSet.getInt("ProductId"),
                        resultSet.getString("ProdName")
                ));
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return productArrayList;
    }

    public static Product getProductById(int productId) {
        Product product = null;
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM products WHERE ProductId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Assign parameter
            stmt.setInt(1, productId);
            // Execute query
            ResultSet resultSet = stmt.executeQuery();
            // Get first row of data
            if (resultSet.next()) {
                product = new Product(
                    resultSet.getInt("ProductId"),
                    resultSet.getString("ProdName")
                );
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return product;
    }

    public static void updateProduct (Product oldProduct, Product newProduct) {
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "UPDATE products SET ProdName=? " +
                    "WHERE ProductId=? AND ProdName=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Set parameters
            stmt.setString(1,newProduct.getProdName());
            stmt.setInt(2, newProduct.getProductId());
            stmt.setString(3,oldProduct.getProdName());
            // Execute query
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to update products table");
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Product addProduct(Product product) {
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "INSERT INTO products (ProdName) " +
                    "VALUES(?)";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // Set parameters
            stmt.setString(1, product.getProdName());
            // Execute query
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to add to products table");
            } else {
                // Get Id from inserted row
                try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        product.setProductId(resultSet.getInt("ProductId"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

}
