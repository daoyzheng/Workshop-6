/*
Purpose: Domain entity for productSupplier
Author:  Dao Zheng
Date: May, 2019
 */

package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;

public class ProductSupplier {
    private SimpleIntegerProperty productSupplierId;
    private SimpleIntegerProperty productId;
    private SimpleIntegerProperty supplierId;

    public ProductSupplier(int productSupplierId, int productId, int supplierId) {
        this.productSupplierId = new SimpleIntegerProperty(productSupplierId);
        this.productId = new SimpleIntegerProperty(productId);
        this.supplierId = new SimpleIntegerProperty(supplierId);
    }

    public ProductSupplier(int productId, int supplierId) {
        this.productSupplierId = new SimpleIntegerProperty();
        this.productId = new SimpleIntegerProperty(productId);
        this.supplierId = new SimpleIntegerProperty(supplierId);
    }

    public int getProductSupplierId() {
        return productSupplierId.getValue();
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId.set(productSupplierId);
    }

    public int getProductId() {
        return productId.getValue();
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public int getSupplierId() {
        return supplierId.getValue();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }
}
