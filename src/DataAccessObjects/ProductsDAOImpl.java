package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductsDAOImpl implements ProductsDAO {

    @Override
    public ArrayList<Product> getAllProducts() {
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

                conn.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return productArrayList;
    }

    @Override
    public Product getProductById(int productId) {
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void updateProduct (Product oldProduct, Product newProduct) {
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
                throw new Exception("Failled to update products table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product addProduct(Product product) {
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "INSERT INTO products (ProdName)" +
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }

}
