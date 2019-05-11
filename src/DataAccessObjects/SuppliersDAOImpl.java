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

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {

    }

    @Override
    public boolean deleteSupplier(Supplier supplier) {

    }
}
