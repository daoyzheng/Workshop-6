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
            }
            conn.close();
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

    public static void addSupplier(Supplier supplier) {
        int genKey = 0;
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();

            // Generate a default supplier Id
            String selectQuery = "SELECT max(SupplierId) from Suppliers";
            // Create select statement
            Statement stmt = conn.createStatement();
            // Execute select statement
            ResultSet selectResult = stmt.executeQuery(selectQuery);
            if (selectResult.next()) {
                genKey = selectResult.getInt(1) + 1;
            }

            // Create query string to insert
            String query = "INSERT INTO suppliers (SupplierId, SupName) " +
                    "VALUES(?,?)";
            // Create prepare statement
            PreparedStatement pstmt = conn.prepareStatement(query);
            // Assign parameters
            pstmt.setInt(1, genKey);
            pstmt.setString(2, supplier.getSupName());
            // Execute query
            int numRows = pstmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to add to suppliers table");
            } else {
                supplier.setSupplierId(genKey);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean deleteSupplier(Supplier supplier) {
//
//    }
}
