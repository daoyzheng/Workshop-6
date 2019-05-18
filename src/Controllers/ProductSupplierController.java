package Controllers;

import java.net.URL;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DataAccessObjects.ProductManager;
import DataAccessObjects.ProductSupplierManager;
import DataAccessObjects.SupplierManager;
import DomainEntities.Product;
import DomainEntities.ProductSupplier;
import DomainEntities.Supplier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class ProductSupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Product> cbProduct;

    @FXML
    private ListView<Supplier> lvSupplier;

    // ObservableList of Products
    ObservableList<Product> prodObservableList = FXCollections.observableArrayList();
    // Array list of product suppliers
    ArrayList<ProductSupplier> productSupplierArrayList = new ArrayList<>();
    @FXML
    void initialize() {
        // Get all products and populate products' combo box
        prodObservableList.addAll(ProductManager.getAllProducts());
        cbProduct.setItems(prodObservableList);

        productSupplierArrayList.addAll(ProductSupplierManager.getAllProductsSuppliers());

        // Add selected item event listener to combo box
        cbProduct.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                int productId = newValue.getProductId();
                // Get a list of supplier ids associated with the productId
                ArrayList<Integer> supplierIdArrayList = ProductSupplierManager.getSupplierIdsByProductId(productId);

                // Now get a list of supplier objects using the supplierIdArrayList
                ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();
                for(Integer supplierId: supplierIdArrayList) {
                    supplierObservableList.add(SupplierManager.getSupplierById(supplierId));
                }
                // Set list view items
                lvSupplier.setItems(supplierObservableList);
            }
        });
    }
}
