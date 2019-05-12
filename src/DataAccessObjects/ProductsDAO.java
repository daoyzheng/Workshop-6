package DataAccessObjects;

import DomainEntities.Product;

import java.util.ArrayList;

public interface ProductsDAO {
    public ArrayList<Product> getAllProducts();
    public Product getProductById(int productId);
    public boolean update
}
