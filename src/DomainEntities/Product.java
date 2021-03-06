/*
Purpose: Domain entity for product
Author:  Dao Zheng
Date: May, 2019
 */


package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private SimpleIntegerProperty productId;
    private SimpleStringProperty prodName;

    public Product(int productId, String prodName) {
        this.productId = new SimpleIntegerProperty(productId);
        this.prodName = new SimpleStringProperty(prodName);
    }

    public Product(String prodName) {
        this.productId = new SimpleIntegerProperty();
        this.prodName = new SimpleStringProperty(prodName);
    }

    public int getProductId() {
        return productId.getValue();
    }

    @Override
    public String toString() {
        return prodName.getValue();
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public String getProdName() {
        return prodName.getValue();
    }

    public void setProdName(String prodName) {
        this.prodName.set(prodName);
    }
}
