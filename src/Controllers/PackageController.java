package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

import javax.xml.soap.SOAPPart;

public class PackageController {

    @FXML
    private TabPane tapPaneMain;

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
    private Tab tabEdit;

    @FXML
    private Button btnEditBack;

    @FXML
    private Button btnEditSaveBack;

    @FXML
    private Button btnEditSaveEdit;

    @FXML
    private Button btnEditUndo;

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
    private CheckBox pkgActiveEdit;

    @FXML
    private Tab tabAdd;

    @FXML
    private Button btnAddBack;

    @FXML
    private Button btnAddSaveBack;

    @FXML
    private Button btnAddSaveAdd;

    @FXML
    private Button btnAddReset;

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

    @FXML
    private Tab tabAddEdit;

    @FXML
    private Button btnAddEditBack;

    @FXML
    private Button btnAddEditSaveBack;

    @FXML
    private Button btnAddEditBackReset;

    @FXML
    private TableView<?> tvPdcSpl1;

    @FXML
    private TableColumn<?, ?> colProduct1;

    @FXML
    private TableColumn<?, ?> colSupplier1;

    @FXML
    private Button btnAddEditDelete;

    @FXML
    private Button btnAddEditDeleteAll;

    @FXML
    private TableView<?> tvPdcSpl2;

    @FXML
    private Button btnAddEditAdd;

    @FXML
    private Button btnAddEditAddAll;



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

        firstPackage();

        selectToEdit();


    }


    // a method to load all packages from database to the table view
    private void loadPackages()
    {
        ObservableList<Package> packages;
        packages = PackageManager.getAllPackages();
        tvPackages.setItems(packages);
    }


    // a method to show a given package on the Edit tab
    private void loadPackage()
    {
        Package pkg = tvPackages.getSelectionModel().getSelectedItem();
        pkgNameEdit.setText(pkg.getPkgName());
        pkgStartDateEdit.setValue(pkg.getPkgStartDate());
        pkgEndDateEdit.setValue(pkg.getPkgEndDate());
        pkgDescEdit.setText(pkg.getPkgDesc());
        pkgBasePriceEdit.setText(String.valueOf(pkg.getPkgBasePrice()));
        pkgAgencyCommissionEdit.setText(Double.toString(pkg.getPkgAgencyCommission()));
        if (!pkg.isActive())
            pkgActiveEdit.setSelected(false);
    }


    // a method to pre-select the first package in the table
    // and show it on the Edit tab
    private void firstPackage() {
        tvPackages.getSelectionModel().select(0);
        loadPackage();
    }


    // a method to show the selected package on the Edit tab
    private void selectToEdit()
    {
        tvPackages.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadPackage();
                if (event.getClickCount() == 2) //Checking double click
                {
                    SelectionModel<Tab> selectionModel = tapPaneMain.getSelectionModel();
                    selectionModel.select(tabEdit);
                }
            }
        });
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
//    @FXML
//    Void tabEditClicked (MouseEvent event)
//    {
//
//    }


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
}