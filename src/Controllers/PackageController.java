package Controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import DomainEntities.PackageProductSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import DataAccessObjects.PackageManager;
import DataAccessObjects.ProductSupplierManager;
import DomainEntities.Package;
import DomainEntities.PackageProductSupplier;


public class PackageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<PackageProductSupplier> lvProductsSuppliers;

    @FXML
    private TableView<Package> tvPackages;

    @FXML
    private TableColumn<Package, Integer> PackageId;

    @FXML
    private TableColumn<Package, String> pkgName;

    @FXML
    private TableColumn<Package, ObjectProperty<Date>> pkgStartDate;

    @FXML
    private TableColumn<Package, Date> pkgEndDate;

    @FXML
    private TableColumn<Package, String> pkgDesc;

    @FXML
    private TableColumn<Package, Double> pkgBasePrice;

    @FXML
    private TableColumn<Package, Double> pkgAgencyCommission;

    @FXML
    private TableColumn<Package, Boolean> active;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField tfSearch;

    @FXML
    private Tab gpEdit;

    @FXML
    private Button btnCloseEdit;

    @FXML
    private Button btnSaveCloseEdit;

    @FXML
    private Button btnSaveEdit;

    @FXML
    private Button btnUndo;

    @FXML
    private GridPane grPaneEdit;

    @FXML
    private ColumnConstraints gpDetails1;

    @FXML
    private TextField pkgNameEdit;

    @FXML
    private TextField pkgDescEdit;

    @FXML
    private TextField pkgBasePriceEdit;

    @FXML
    private TextField pkgAgencyCommissionEdit;

    @FXML
    private Label lbErPkgNameEdit;

    @FXML
    private Label lbErPkgSDatEdit;

    @FXML
    private Label lbErPkgEDatEdit;

    @FXML
    private Label lbErPkgBPriceEdit;

    @FXML
    private Label lbErPkgCommissionEdit;

    @FXML
    private DatePicker pkgStartDateEdit;

    @FXML
    private DatePicker pkgEndDateEdit;

    @FXML
    private CheckBox activeEdit;

    @FXML
    private Tab gpAdd;

    @FXML
    private Button btnCloseAdd;

    @FXML
    private Button btnSaveCloseAdd;

    @FXML
    private Button btnSaveAdd;

    @FXML
    private Button btnReset;

    @FXML
    private GridPane grPaneAdd;

    @FXML
    private ColumnConstraints gpDetails11;

    @FXML
    private TextField pkgNameAdd;

    @FXML
    private TextField pkgDescAdd;

    @FXML
    private TextField pkgBasePriceAdd;

    @FXML
    private TextField pkgAgencyCommissionAdd;

    @FXML
    private Label lbErPkgNameAdd;

    @FXML
    private Label lbErPkgSDatAdd;

    @FXML
    private Label lbErPkgEDatAdd;

    @FXML
    private Label lbErPkgBPriceAdd;

    @FXML
    private Label lbErPkgCommissionAdd;

    @FXML
    private DatePicker pkgStartDateAdd;

    @FXML
    private DatePicker pkgEndDateAdd;

    @FXML
    private CheckBox activeAdd;
    private Main main;

    @FXML
    void btnDetailCloseClicked(MouseEvent event) {

    }

    @FXML
    void btnDetailSaveClicked(MouseEvent event) {

    }

    @FXML
    void btnDetailSaveCloseClicked(MouseEvent event) {

    }

    @FXML
    void btnDetailUndoClicked(MouseEvent event) {

    }

    @FXML
    void btnNavAddClick(MouseEvent event) {

    }

    @FXML
    void btnNavEditClick(MouseEvent event) {

    }

    @FXML
    void tfNavSearchKeyTyped(KeyEvent event) {

    }


    @FXML
    void initialize() {

        //setup up table cell factories
        PackageId.setCellValueFactory(cellData -> cellData.getValue().packageIdProperty().asObject());
        pkgName.setCellValueFactory(cellData -> cellData.getValue().pkgNameProperty());
        pkgStartDate.setCellValueFactory(cellData -> cellData.getValue().pkgStartDateProperty());
        pkgEndDate.setCellValueFactory(cellData -> cellData.getValue().pkgEndDateProperty());
        pkgDesc.setCellValueFactory(cellData -> cellData.getValue().pkgDescProperty());
        pkgBasePrice.setCellValueFactory(cellData -> cellData.getValue().pkgBasePriceProperty().asObject());
        pkgAgencyCommission.setCellValueFactory(cellData -> cellData.getValue().pkgAgencyCommissionProperty().asObject());
        active.setCellValueFactory(cellData -> cellData.getValue().activeProperty());

        loadPackages();
    }


    private void loadPackages()
    {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        packages = PackageManager.getAllPackages();
        tvPackages.setItems(packages);
    }

    public void setMain(Main main)
    {
        this.main = main;
    }
}