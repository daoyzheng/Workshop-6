/*
Purpose: Domain entity class for packages_products_suppliers
Author:  Hoora
Date: June, 2019
 */

package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductSupplierNames {

    // properties:
    private SimpleIntegerProperty ProductSupplierId;
    private SimpleStringProperty ProductName;
    private SimpleStringProperty SupplierName;


    // constructors:
    public ProductSupplierNames() {
        this.ProductSupplierId = new SimpleIntegerProperty();
        this.ProductName = new SimpleStringProperty();
        this.SupplierName = new SimpleStringProperty();
    }

    public ProductSupplierNames(int productSupplierId, String productName, String supplierName) {
        this.ProductSupplierId = new SimpleIntegerProperty(productSupplierId);
        this.ProductName = new SimpleStringProperty(productName);
        this.SupplierName = new SimpleStringProperty(supplierName);
    }


    // getters-setters:

    public int getProductSupplierId() {
        return ProductSupplierId.get();
    }

    public SimpleIntegerProperty productSupplierIdProperty() {
        return ProductSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.ProductSupplierId.set(productSupplierId);
    }

    public String getProductName() {
        return ProductName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName.set(productName);
    }

    public String getSupplierName() {
        return SupplierName.get();
    }

    public SimpleStringProperty supplierNameProperty() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        this.SupplierName.set(supplierName);
    }


    // toString:

    @Override
    public String toString() {
        return getProductName() + ", " + getSupplierName();
    }
}