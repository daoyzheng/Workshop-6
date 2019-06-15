package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessObjects.ProductManager;
import DomainEntities.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        }

    }

    @FXML
    void initialize() {
        assert tfNewProd != null : "fx:id=\"tfNewProd\" was not injected: check your FXML file 'addProduct.fxml'.";

    }
}

