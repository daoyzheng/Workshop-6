/*
Purpose: Domain entity class for packages_products_suppliers
Author:  Hoora
Date: May, 2019
 */

package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PackageProductSupplier {


//    // properties:
//    private SimpleStringProperty ProductName;
//    private SimpleStringProperty SupplierName;
//
//
//
//    // constructor:
//    public PackageProductSupplier() {
//        this.ProductName = new SimpleStringProperty();
//        this.SupplierName = new SimpleStringProperty();
//    }
//
//    public PackageProductSupplier(String productName, String supplierName) {
//        this.ProductName = new SimpleStringProperty(productName);
//        this.SupplierName = new SimpleStringProperty(supplierName);
//    }
//
//
//    // getters-setters:
//    public String getProductName() {
//        return ProductName.get();
//    }
//
//    public SimpleStringProperty productNameProperty() {
//        return ProductName;
//    }
//
//    public void setProductName(String productName) {
//        this.ProductName.set(productName);
//    }
//
//    public String getSupplierName() {
//        return SupplierName.get();
//    }
//
//    public SimpleStringProperty supplierNameProperty() {
//        return SupplierName;
//    }
//
//    public void setSupplierName(String supplierName) {
//        this.SupplierName.set(supplierName);
//    }
//
//
//    // toString:
//    @Override
//    public String toString() {
//        return getProductName() + getSupplierName();
//    }




    // properties:
    private SimpleIntegerProperty PackageId;
    private SimpleIntegerProperty ProductSupplierId;
    private SimpleStringProperty ProductName;
    private SimpleStringProperty SupplierName;



    // constructor:
    public PackageProductSupplier() {
        this.PackageId = new SimpleIntegerProperty();
        this.ProductSupplierId = new SimpleIntegerProperty();
        this.ProductName = new SimpleStringProperty();
        this.SupplierName = new SimpleStringProperty();
    }

    public PackageProductSupplier(int packageId, int productSupplierId, String productName, String supplierName) {
        this.PackageId = new SimpleIntegerProperty(packageId);
        this.ProductSupplierId = new SimpleIntegerProperty(productSupplierId);
        this.ProductName = new SimpleStringProperty(productName);
        this.SupplierName = new SimpleStringProperty(supplierName);
    }


    // getters-setters:
    public int getPackageId() {
        return PackageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        this.PackageId.set(packageId);
    }

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
        return getProductName() +", " + getSupplierName();
    }
}