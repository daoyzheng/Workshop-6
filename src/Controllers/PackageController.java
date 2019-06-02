package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

import DomainEntities.PackageProductSupplier;
import DataAccessObjects.PackageManager;
import DomainEntities.Package;

public class PackageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfSearch;

    @FXML
    private ListView<PackageProductSupplier> lvProductsSuppliers;

    @FXML
    private TableView<Package> tvPackages;

    @FXML
    private TableColumn<Package, Integer> PackageId;

    @FXML
    private TableColumn<Package, String> pkgName;

    @FXML
    private TableColumn<Package, LocalDate> pkgStartDate;

    @FXML
    private TableColumn<Package, LocalDate> pkgEndDate;

    @FXML
    private TableColumn<Package, String> pkgDesc;

    @FXML
    private TableColumn<Package, Double> pkgBasePrice;

    @FXML
    private TableColumn<Package, Double> pkgAgencyCommission;

    @FXML
    private TableColumn<Package, Boolean> active;

    @FXML
    private Tab tapEdit;

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


    // class-level variables:
    Package curntPkg = new Package(); // current (selected) package in the package scene

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

        curntPkg = firstPackage();
    }


    // a method to find the first package in the table as the selected package
    private Package firstPackage() {

    }


    private void loadPackages()
    {
        ObservableList<Package> packages;
        packages = PackageManager.getAllPackages();
        tvPackages.setItems(packages);
    }


    @FXML
    void btnCloseAddClicked(MouseEvent event) {

    }

    @FXML
    void btnCloseEditClicked(MouseEvent event) {

    }

    @FXML
    void btnResetClicked(MouseEvent event) {

    }

    @FXML
    void btnSaveAddClicked(MouseEvent event) {

    }

    @FXML
    void btnSaveCloseAddClicked(MouseEvent event) {

    }

    @FXML
    void btnSaveCloseEditClicked(MouseEvent event) {

    }

    @FXML
    void btnSaveEditClicked(MouseEvent event) {

    }

    @FXML
    void btnUndoClicked(MouseEvent event) {

    }

    @FXML
    void tfNavSearchKeyTyped(KeyEvent event) {

    }



//    public void setMain(Main main)
//    {
//        this.main = main;
//    }




//    @FXML
//    void tfNavSearchKeyTyped(KeyEvent event) {
//        System.out.println("pressed");
//
//        String strFilter = tfNavSearch.getText();
//
//        List<Agency> filteredList = navTableArrayList.stream()
//                .filter(d -> d.toString().contains(strFilter))
//                .collect(Collectors.toList());
//
//        System.out.println(filteredList);
//
//        ObservableList<Agency> data = FXCollections.observableArrayList(filteredList);
//        tvNavTable.setItems(data);
//
//    }
}