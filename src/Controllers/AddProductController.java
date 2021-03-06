package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DataAccessObjects.ProductManager;
import DataAccessObjects.ProductSupplierManager;
import DomainEntities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfNewProd;

    @FXML
    private Button btnAddProduct;

    public ObservableList<Product> productObservableList;

    public void setProductObservableList(ObservableList<Product> observableList) {
        this.productObservableList = observableList;
    }

    private ListView<Product> productListView;

    public void setProductListView(ListView<Product> productListView) {
        this.productListView = productListView;
    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        boolean validInsert = true;
        // Grab new supplier name from text field
        String newProdName = tfNewProd.getText();
        // Validate if supplier name exist already
        for (Product product : productObservableList) {
            if (newProdName.toLowerCase().equals(product.getProdName().toLowerCase())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Product already exists");
                alert.showAndWait();
                validInsert = false;
            }
        }

        if (validInsert) {
            // Create product object and add to Products table
            Product newProd = new Product(newProdName);
            ProductManager.addProduct(newProd);

            // Refresh list view
            // Get all products
            ArrayList<Product> productArrayList = ProductManager.getAllProducts();
            productListView.setItems(FXCollections.observableArrayList(productArrayList));

            // get a handle to the stage
            Stage stage = (Stage) btnAddProduct.getScene().getWindow();
            // Close current window
            stage.close();
        }

    }

    @FXML
    void initialize() {
        assert tfNewProd != null : "fx:id=\"tfNewProd\" was not injected: check your FXML file 'addProduct.fxml'.";

    }
}

