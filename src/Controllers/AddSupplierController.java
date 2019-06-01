package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import DataAccessObjects.ProductSupplierManager;
import DataAccessObjects.SupplierManager;
import DomainEntities.Product;
import DomainEntities.ProductSupplier;
import DomainEntities.Supplier;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddSupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radioNewSupplier;

    @FXML
    private RadioButton radioExistingSupplier;

    @FXML
    private TextField tfNewSupplier;

    @FXML
    private ToggleGroup tgSupplier;

    @FXML
    private ComboBox<Supplier> cbSupplier;

    @FXML
    private Button btnAddSupplier;

    @FXML
    private Label lbProductName;

    private Product selectedProduct;

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    private ObservableList<Supplier> selectedSuppliers;

    public void setSelectedSuppliers(ObservableList<Supplier> selectedSuppliers) {
        this.selectedSuppliers = selectedSuppliers;
    }

    private ListView<Product> lvProduct;

    public void setLvProduct(ListView<Product> lvProduct) {
        this.lvProduct = lvProduct;
    }

    private boolean isExisting = true;

    private boolean addSuccess = true;

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        if (isExisting) {
            boolean validInsert = true;
            // If user is choosing from existing suppliers
            // Get chosen supplier object
            Supplier selectedSupplier = cbSupplier.getSelectionModel().getSelectedItem();
            // Add the combination of supplier and product to product supplier table
            // Need to validate if combination already exist in the database
            for (Supplier supplier: selectedSuppliers) {
                if (selectedSupplier.getSupplierId() == supplier.getSupplierId()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Supplier already exists");
                    alert.showAndWait();
                    validInsert = false;
                } else {
                    validInsert = true;
                }
            }

            if(validInsert) {
                // If no duplicates exist, then we can safely add to the product supplier table
                addSuccess = ProductSupplierManager.addProductSupplier(new ProductSupplier(
                        selectedProduct.getProductId(),
                        selectedSupplier.getSupplierId()
                ));
            }
        } else { // If adding a new Supplier
            boolean validInsert = true;
            // Grab new supplier name from text field
            String newSupplierName = tfNewSupplier.getText();
            // Validate if supplier name exist already
            for (Supplier supplier : selectedSuppliers) {
                if (newSupplierName.equals(supplier.getSupName())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Supplier already exists");
                    alert.showAndWait();
                    validInsert = false;
                } else {
                    validInsert = true;
                }
            }

            if (validInsert) {
                // Create supplier object and add to Suppliers table
                Supplier newSupplier = new Supplier(newSupplierName);
                SupplierManager.addSupplier(newSupplier);

                // Now add to the product supplier table
                addSuccess = ProductSupplierManager.addProductSupplier(new ProductSupplier(
                        selectedProduct.getProductId(),
                        newSupplier.getSupplierId()
                ));
            }
        }

        if (addSuccess) {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            // Refresh the supplier list view
            lvProduct.getSelectionModel().selectFirst();
            lvProduct.getSelectionModel().select(selectedProduct);

            stage.close();
        }
    }

    @FXML
    void initialize() {
        // Display product name
        //lbProductName.setText(selectedProduct.getProdName());
        // Populate existing supplier combo box, but set it to disable by default
        ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();
        supplierObservableList.addAll(SupplierManager.getAllSuppliers());
        cbSupplier.setItems(supplierObservableList);
        // Enable combo box by default
        cbSupplier.setDisable(false);
        // Disable text field by default
        tfNewSupplier.setDisable(true);
        // Add change event listener to supplier toggle group
        tgSupplier.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                if (selectedRadioButton.getId().equals("radioExistingSupplier")) {
                    cbSupplier.setDisable(false);
                    tfNewSupplier.setDisable(true);
                    isExisting = true;
                } else {
                    cbSupplier.setDisable(true);
                    tfNewSupplier.setDisable(false);
                    isExisting = false;
                }
            }
        });
    }
}
