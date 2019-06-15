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
    private Label labelProd;

    public Label getLabelProd() {
        return this.labelProd;
    }

    private Product selectedProduct;

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    private ObservableList<Supplier> selectedSuppliers;

    public void setSelectedSuppliers(ObservableList<Supplier> selectedSuppliers) {
        this.selectedSuppliers = selectedSuppliers;
    }

    private boolean isExisting = true;

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
                }
            }

            if(validInsert) {
                // If no duplicates exist, then we can safely add to the product supplier table
                ProductSupplierManager.addProductSupplier(new ProductSupplier(
                        selectedProduct.getProductId(),
                        selectedSupplier.getSupplierId()
                ));

                // Refresh list view
                // Get ProductSupplierController
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/ProductSupplier.fxml"));
                try {
                    Parent prodSupplier = fxmlLoader.load();
                    ProductSupplierController productSupplierController = fxmlLoader.getController();

                    // Refresh product list view
                    ListView<Product> productListView = productSupplierController.getLvProduct();

                    productListView.getSelectionModel().select(selectedProduct);

                    // Refresh supplier list view
//                    ListView<Supplier> supplierListView = productSupplierController.getLvSupplier();
//                    supplierListView.getSelectionModel().selectFirst();
//                    supplierListView.getSelectionModel().select(selectedSupplier);

                    // get a handle to the stage
                    Stage stage = (Stage) btnAddSupplier.getScene().getWindow();
                    // Close current window
                    stage.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
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
                ProductSupplierManager.addProductSupplier(new ProductSupplier(
                        selectedProduct.getProductId(),
                        newSupplier.getSupplierId()
                ));
            }
        }
    }

    @FXML
    void initialize() {
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

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        cbSupplier.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                supplierObservableList.removeAll(supplierObservableList);
                supplierObservableList.addAll(SupplierManager.getAllSuppliers());
            } else {
                supplierObservableList.removeAll(supplierObservableList);
                supplierObservableList.addAll(SupplierManager.getSupplierByKeyWord(newValue));
            }
        });

        cbSupplier.setItems(supplierObservableList);
    }
}
