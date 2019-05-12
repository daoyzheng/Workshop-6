package DataAccessObjects;

import DomainEntities.ProductSupplier;

import java.util.ArrayList;

public interface ProductsSuppliersDAO {
    ArrayList<ProductSupplier> getAllProductsSuppliers();
    ProductSupplier getProductSupplierById(int productSupplierId);
    void updateProductSupplier(ProductSupplier oldProductSupplier, ProductSupplier newProductSupplier);
    ProductSupplier addProductSupplier(ProductSupplier productSupplier);

}
