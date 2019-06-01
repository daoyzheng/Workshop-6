package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import DataAccessObjects.ProductManager;
import DomainEntities.Product;
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
    private TextField tfNewProductName;

    private Product selectedProduct;

    public void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    @FXML
    private Button btnAddProduct;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        // check if name entered is equal to selected product name
        if (tfNewProductName.getText().equals(selectedProduct.getProdName())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product Name already exists");
            alert.showAndWait();
        } else {
            // Add new product to product's table
            ProductManager.addProduct(new Product(
                    tfNewProductName.getText()
            ));
        }
    }

    @FXML
    void initialize() {
    }
}

