package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuppliersDAOImpl implements SuppliersDAO {

    @Override
    public ArrayList<Supplier> getAllSuppliers() {
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

    @Override
    public Supplier getSupplierById(int supplierId) {
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

            supplier = new Supplier(
                resultSet.getInt("SupplierId"),
                resultSet.getString("SupName")
            );

            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    @Override
    public boolean updateSupplier(Supplier oldSupplier, Supplier newSupplier) {
        boolean updated = true;

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
                updated = false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return updated;
    }

//    @Override
//    public boolean deleteSupplier(Supplier supplier) {
//
//    }
}
