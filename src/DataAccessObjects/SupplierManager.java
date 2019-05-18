/*
Purpose: Data Access Class for Suppliers
Author:  Dao Zheng
Date: May, 2019
 */

package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Supplier;

import java.sql.*;
import java.util.ArrayList;

public class SupplierManager {

    public static ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM suppliers";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Execute Statement
            ResultSet resultSet = stmt.executeQuery();
            // Populate supplier array list
            while (resultSet.next()) {
                supplierArrayList.add(new Supplier(
                        resultSet.getInt("SupplierId"),
                        resultSet.getString("SupName")
                ));

                conn.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return supplierArrayList;
    }

    public static Supplier getSupplierById(int supplierId) {
        Supplier supplier = null;

        try {
            // Get Db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM suppliers WHERE SupplierId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Assign parameter for prepare statement
            stmt.setInt(1,supplierId);
            // Execute statement
            ResultSet resultSet = stmt.executeQuery();
            // Get first row of data
            if (resultSet.next()) {
                supplier = new Supplier(
                    resultSet.getInt("SupplierId"),
                    resultSet.getString("SupName")
                );
            }

            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    public static void updateSupplier(Supplier oldSupplier, Supplier newSupplier) {
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "UPDATE suppliers SET SupName=? WHERE SupplierId=? " +
                    "AND SupName=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Assign parameter
            stmt.setString(1,newSupplier.getSupName());
            stmt.setInt(2,newSupplier.getSupplierId());
            stmt.setString(3, oldSupplier.getSupName());
            // Execute query
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to update to suppliers table");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Supplier addSupplier(Supplier supplier) {
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "INSERT INTO suppliers (SupName) " +
                    "VALUES(?)";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // Assign parameters
            stmt.setString(1, supplier.getSupName());
            // Execute query
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to add to suppliers table");
            } else {
                // Get the Id back from new insert
                try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        supplier.setSupplierId(resultSet.getInt("SupplierId"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return supplier;
    }

//    @Override
//    public boolean deleteSupplier(Supplier supplier) {
//
//    }
}
