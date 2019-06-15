package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import DataAccessObjects.ProductManager;
import DataAccessObjects.ProductSupplierManager;
import DataAccessObjects.SupplierManager;
import DomainEntities.Product;
import DomainEntities.ProductSupplier;
import DomainEntities.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProductSupplierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddSupplier;

    @FXML
    private Button btnUpdateSupplier;

    @FXML
    private ListView<Product> lvProduct;

    @FXML
    private ListView<Supplier> lvSupplier;

    @FXML
    private RadioButton radioProducts;

    @FXML
    private ToggleGroup tgProductSupplier;

    @FXML
    private RadioButton radioSuppliers;

    @FXML
    private TextField tfSearchProduct;

    @FXML
    private TextField tfSearchSupplier;

    @FXML
    private Button btnAddProduct;

    // Allow public access of list view reference from other controllers
    public ListView<Supplier> getLvSupplier() {
        return this.lvSupplier;
    }

    public ListView<Product> getLvProduct() {
        return this.lvProduct;
    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/addProduct.fxml"));
        Parent addProduct = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(addProduct));
        stage.show();

        // Get AddSupplierController
        AddProductController controller = fxmlLoader.getController();
        // Pass current list view array to addProductController
        controller.setProductObservableList(lvProduct.getItems());
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/addSupplier.fxml"));
        Parent addSupplier = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Supplier");
        stage.setScene(new Scene(addSupplier));
        // If no product is selected, show alert instead
        Product selectedProd = lvProduct.getSelectionModel().getSelectedItem();
        if (selectedProd == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product first");
            alert.showAndWait();
        } else {
            stage.show();
        }

        // Get AddSupplierController
        AddSupplierController controller = fxmlLoader.getController();
        // Pass selected product Object to addSupplierController
        controller.setSelectedProduct(selectedProd);
        // Pass current list view array to addSupplierController
        controller.setSelectedSuppliers(lvSupplier.getItems());
        // Set label text
        controller.getLabelProd().setText(selectedProd.getProdName());
        // Pass observablearray of products to addSupplierController
        //controller.setProductObservableList(prodObservableList);
    }

    // ObservableList of Products
    ObservableList<Product> prodObservableList = FXCollections.observableArrayList();
    // ObservableList of Suppliers
    ObservableList<Supplier> suppObservableList = FXCollections.observableArrayList();
    // Array list of product suppliers
    ArrayList<ProductSupplier> productSupplierArrayList = new ArrayList<>();
    // Status for selected radio buttons
    boolean productSelected = true;
    boolean supplierSelected = false;
    @FXML
    void initialize() {
        // Load products' list view
        loadProducts();

        // Get all product suppliers from product supplier table
        productSupplierArrayList.addAll(ProductSupplierManager.getAllProductsSuppliers());

        // Add change event listener to product supplier toggle group
        tgProductSupplier.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton selectedListView = (RadioButton) newValue;
            if (selectedListView.getId().equals("radioProducts")) {
                productSelected = true;
                supplierSelected = false;
                loadProducts();
                // Clear suppliers' list view
                lvSupplier.getItems().clear();
                // Clear tfSearchProduct
                tfSearchProduct.clear();
                // Clear tfSearchSupplier
                tfSearchSupplier.clear();
            } else {
                productSelected = false;
                supplierSelected = true;
                loadSuppliers();
                // Clear products' list view
                lvProduct.getItems().clear();
                // Clear tfSearchProduct
                tfSearchProduct.clear();
                // Clear tfSearchSupplier
                tfSearchSupplier.clear();
            }
        });

        // Add selected item event listener to lvProduct
        lvProduct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (productSelected && (newValue != null)) {
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

        // Add selected item event listener to lvSupplier
        lvSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (supplierSelected && (newValue != null)) {
                int supplierId = newValue.getSupplierId();
                // Get an arraylist of productIds associated with the supplierId
                ArrayList<Integer> productIdArrayList = ProductSupplierManager.getProductIdsBySupplierId(supplierId);

                // Now get a list of product objects using the productIdArrayList
                ObservableList<Product> productObservableList = FXCollections.observableArrayList();
                for(Integer productId: productIdArrayList) {
                    productObservableList.add(ProductManager.getProductById(productId));
                }
                // Set list view items
                lvProduct.setItems(productObservableList);
            }
        });

        // Add Change listener to search product text field
        tfSearchProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            // If search field is empty, load the whole product list
            if (productSelected && (newValue == null)) {
                loadProducts();
            } else if (productSelected) {
                // First clear the prodObservableList
                prodObservableList.clear();
                // Now load search returns from keyword
                prodObservableList.addAll(ProductManager.getProductByKeyWord(newValue));
                // assign prodObservableList to product list view
                lvProduct.setItems(prodObservableList);
            }
        });

        // Add change listener to search supplier text field
        tfSearchSupplier.textProperty().addListener((observable, oldValue, newValue) -> {
            // If search field is empty, load the whole supplier list
            if (supplierSelected && (newValue == null)) {
                loadSuppliers();
            } else if (supplierSelected) {
                // First clear the suppObservableList
                suppObservableList.clear();
                // Now load search returns from keyword
                suppObservableList.addAll(SupplierManager.getSupplierByKeyWord(newValue));
                // assign suppObservableList to supplier list view
                lvSupplier.setItems(suppObservableList);
            }
        });
    }

    /**
     *  Get all products and populate products' listView
      */
    public void loadProducts() {
        // clear prodObservableList if it's not empty
        prodObservableList.clear();
        prodObservableList.addAll(ProductManager.getAllProducts());
        lvProduct.setItems(prodObservableList);
    }

    /**
     *  Get all suppliers and populate suppliers' listView
     */
    public void loadSuppliers() {
        // clear suppObservableList if it's not empty
        suppObservableList.clear();
        suppObservableList.addAll(SupplierManager.getAllSuppliers());
        lvSupplier.setItems(suppObservableList);
    }
}
