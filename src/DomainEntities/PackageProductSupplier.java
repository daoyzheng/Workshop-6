/*
Purpose: Domain entity class for packages_products_suppliers
Author:  Hoora
Date: May, 2019
 */

package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;

public class PackageProductSupplier {


    // properties:
    private SimpleIntegerProperty PackageId;
    private SimpleIntegerProperty ProductSupplierId;


    // constructor:
    public PackageProductSupplier() {
    }

    public PackageProductSupplier(int packageId, int productSupplierId) {
        PackageId.set(packageId);
        ProductSupplierId.set(productSupplierId);
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


    // toString:
    @Override
    public String toString() {
        return "packages_products_suppliers{" +
                "PackageId=" + PackageId +
                ", ProductSupplierId=" + ProductSupplierId +
                '}';
    }
}