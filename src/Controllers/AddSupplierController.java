package Controllers;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import DataAccessObjects.SupplierManager;
import DomainEntities.Supplier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    void initialize() {
        // Populate existing supplier combo box, but set it to disable by default
        ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();
        supplierObservableList.addAll(SupplierManager.getAllSuppliers());
        cbSupplier.setItems(supplierObservableList);
        cbSupplier.setDisable(true);
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
                } else {
                    cbSupplier.setDisable(true);
                    tfNewSupplier.setDisable(false);
                }
            }
        });
    }
}
