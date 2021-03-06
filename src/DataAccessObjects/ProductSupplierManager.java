/*
Purpose: Data Access Class for productsSuppliers
Author:  Dao Zheng
Date: May, 2019
 */

package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.ProductSupplier;
import DomainEntities.Supplier;

import java.sql.*;
import java.util.ArrayList;

public class ProductSupplierManager {

    // READ
    public static ArrayList<ProductSupplier> getAllProductsSuppliers() {
        ArrayList<ProductSupplier> productSupplierArrayList = new ArrayList<>();
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM products_suppliers";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Execute query
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                productSupplierArrayList.add(new ProductSupplier(
                        resultSet.getInt("ProductSupplierId"),
                        resultSet.getInt("ProductId"),
                        resultSet.getInt("SupplierId")
                ));
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return productSupplierArrayList;
    }

    /**
     *
     * @param productSupplierId
     * @return null reference if unable to read from database
     */
    public static ProductSupplier getProductSupplierById(int productSupplierId) {
        ProductSupplier productSupplier = null;
        try {
            // Get Db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM products_suppliers WHERE ProductSupplierId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Set parameters
            stmt.setInt(1,productSupplierId);
            // Execute query
            ResultSet resultSet = stmt.executeQuery();
            // Get first row of data
            if (resultSet.next()) {
                productSupplier = new ProductSupplier(
                        resultSet.getInt("ProductSupplierId"),
                        resultSet.getInt("ProductId"),
                        resultSet.getInt("SupplierId")
                );
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return productSupplier;
    }

    /**
     * Get an array list of supplierIds associated with the parameter productId
     * @param productId
     * @return arraylist of supplierIds
     */
    public static ArrayList<Integer> getSupplierIdsByProductId(int productId) {
        ArrayList<Integer> supplierIdArrayList = new ArrayList<>();
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT SupplierId FROM products_suppliers WHERE ProductId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Set prepared Statement parameters
            stmt.setInt(1, productId);
            // Execute query
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                supplierIdArrayList.add(
                        resultSet.getInt("SupplierId")
                );
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return supplierIdArrayList;
    }


    public static ArrayList<Integer> getProductIdsBySupplierId(int supplierId) {
        ArrayList<Integer> productIdArrayList = new ArrayList<>();
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT ProductId FROM products_suppliers WHERE SupplierId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Set prepared Statement parameters
            stmt.setInt(1, supplierId);
            // Execute query
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                productIdArrayList.add(
                        resultSet.getInt("ProductId")
                );
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return productIdArrayList;
    }

    /**
     * Updates ProductSupplier table
     * @param oldProductSupplier
     * @param newProductSupplier
     */
    public static void updateProductSupplier(ProductSupplier oldProductSupplier, ProductSupplier newProductSupplier) {
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "UPDATE products_suppliers SET ProductId=?, SupplierId=? " +
                    "WHERE ProductSupplierId=? AND " +
                    "ProductId=? AND SupplierId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Set parameters
            stmt.setInt(1,newProductSupplier.getProductId());
            stmt.setInt(2,newProductSupplier.getSupplierId());
            stmt.setInt(3,oldProductSupplier.getProductSupplierId());
            stmt.setInt(4,oldProductSupplier.getProductId());
            stmt.setInt(5,oldProductSupplier.getSupplierId());
            // Execute query
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to update to ProductSupplier table");
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new product supplier to the database
     * @param productSupplier
     * @return the newly inserted ProductSupplier object
     */
    public static int addProductSupplier(ProductSupplier productSupplier) {
        int numRows = 0;
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "INSERT INTO products_suppliers (ProductId, SupplierId) " +
                    "VALUES(?,?)";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // Set parameters
            stmt.setInt(1,productSupplier.getProductId());
            stmt.setInt(2,productSupplier.getSupplierId());
            // Execute query
            numRows = stmt.executeUpdate();
            if (numRows == 0) {
                return numRows;
            } else {
                // Get id from inserted row
                try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        productSupplier.setProductSupplierId(resultSet.getInt(1));
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
        return numRows;
    }
}
