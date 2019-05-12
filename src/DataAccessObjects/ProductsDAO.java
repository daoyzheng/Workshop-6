package DataAccessObjects;

import DomainEntities.Product;

import java.util.ArrayList;

public interface ProductsDAO {
    public ArrayList<Product> getAllProducts();
    public Product getProductById(int productId);
    public void updateProduct(Product oldProduct, Product newProduct);
    public Product addProduct(Product product);
    // public boolean deleteProduct(Product product);
}
