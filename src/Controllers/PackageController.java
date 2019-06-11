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
    private TabPane tabPaneMain;

//    @FXML
//    private Tab tab;

    @FXML
    private Tab tabMain;

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
    private CheckBox pkgActiveAdd;

    @FXML
    private Tab tabAddEdit;

//    @FXML
//    private TableView<?> tvPdcSpl2;
//
//    @FXML
//    private TableColumn<?, ?> colProduct1;
//
//    @FXML
//    private TableColumn<?, ?> colSupplier1;

    @FXML
    private Button btnAddEditAdd;

    @FXML
    private Button btnAddEditAddAll;

//    @FXML
//    private TableView<?> tvPdcSpl1;

    @FXML
    private Button btnAddEditDelete;

    @FXML
    private Button btnAddEditDeleteAll;

    @FXML
    private Button btnAddEditBack;



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

        firstPackage();
    }


    // a method to show the selected package on the Edit tab
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
        else
            pkgActiveEdit.setSelected(true);
    }


    // a method to pre-select the first package in the table
    // and show it on the Edit tab
    private void firstPackage() {
        tvPackages.getSelectionModel().select(0);
        loadPackage();
    }


    // a method to show the selected package on the Edit tab
    // and set double click settings
    private void selectToEdit()
    {
        tvPackages.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadPackage();
                if (event.getClickCount() == 2) //Checking double click
                {
                    showTab(tabEdit);
                }
            }
        });
    }


    // a method to return the selected package for further editing
    private Package currentPackage()
    {
        Package pkg = tvPackages.getSelectionModel().getSelectedItem();
        return pkg;
    }

    // a method to show different tabs
    private void showTab(Tab tab)
    {
        SelectionModel<Tab> selectionModel = tabPaneMain.getSelectionModel();
        selectionModel.select(tab);
    }


    // a method to create a package from text fields on Edit tab
    private Package readEditedPackage()
    {
        Package pkg = new Package();
        pkg.setPkgName(pkgNameEdit.getText());
        pkg.setPkgStartDate(pkgStartDateEdit.getValue());
        pkg.setPkgEndDate(pkgEndDateEdit.getValue());
        pkg.setPkgDesc(pkgDescEdit.getText());
        pkg.setPkgBasePrice(Double.parseDouble(pkgBasePriceEdit.getText()));
        pkg.setPkgAgencyCommission(Double.parseDouble(pkgAgencyCommissionEdit.getText()));
        if (pkgActiveEdit.isSelected())
            pkg.setActive(true);
        else
            pkg.setActive(false);
        return pkg;
    }


    // a method to create a package from text fields on Add tab
    private Package readAddedPackage()
    {
        Package pkg = new Package();
        pkg.setPkgName(pkgNameAdd.getText());
        pkg.setPkgStartDate(pkgStartDateAdd.getValue());
        pkg.setPkgEndDate(pkgEndDateAdd.getValue());
        pkg.setPkgDesc(pkgDescAdd.getText());
        pkg.setPkgBasePrice(Double.parseDouble(pkgBasePriceAdd.getText()));
        pkg.setPkgAgencyCommission(Double.parseDouble(pkgAgencyCommissionAdd.getText()));
        if (pkgActiveAdd.isSelected())
            pkg.setActive(true);
        else
            pkg.setActive(false);
        return pkg;
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


//****************************************************Edit Tab**********************************************************

    // Back button
    @FXML
    void btnEditBackClicked(MouseEvent event)
    {
        showTab(tabMain);
    }

    // Save & Back button
    @FXML
    void btnEditSaveBackClicked(MouseEvent event)
    {
        Package oldPkg = currentPackage();
        Package newPkg = readEditedPackage();
        newPkg.setPackageId(oldPkg.getPackageId());
        PackageManager.updatePackage(newPkg, oldPkg);
        loadPackages();
        showTab(tabMain);
    }

    // Save & Edit Product-Supplier button
    @FXML
    void btnEditSaveEditClicked(MouseEvent event)
    {
        Package oldPkg = currentPackage();
        Package newPkg = readEditedPackage();
        newPkg.setPackageId(oldPkg.getPackageId());
        PackageManager.updatePackage(newPkg, oldPkg);
        loadPackages();
        showTab(tabAddEdit);
    }

    // Undo changes button
    @FXML
    void btnEditUndoClicked(MouseEvent event)
    {
        loadPackage();
    }

//****************************************************Add Tab**********************************************************

    // Back button
    @FXML
    void btnAddBackClicked(MouseEvent event)
    {
        showTab(tabMain);
    }

    // Save & Back button
    @FXML
    void btnAddSaveBackClicked(MouseEvent event)
    {
        Package pkg = readAddedPackage();
        PackageManager.insertPackage(pkg);
        loadPackages();
        clearAddTab();
        showTab(tabMain);
    }

    // Save & Add Product-Supplier button
    @FXML
    void btnAddSaveAddClicked(MouseEvent event)
    {
        Package pkg = readAddedPackage();
        PackageManager.insertPackage(pkg);
        loadPackages();
        clearAddTab();
        showTab(tabAddEdit);
    }

    // Reset button
    @FXML
    void btnAddResetClicked(MouseEvent event)
    {
        clearAddTab();
    }

    // a method to clear the fields on Add tab
    private void clearAddTab() {
        pkgNameAdd.setText("");
        pkgStartDateAdd.getEditor().clear();
        pkgEndDateAdd.getEditor().clear();
        pkgDescAdd.setText("");
        pkgBasePriceAdd.setText("");
        pkgAgencyCommissionAdd.setText("");
        pkgActiveAdd.setSelected(true);
    }

//****************************************************Add/ Edit Tab*****************************************************

    // Back button
    @FXML
    void btnAddEditBackClicked(MouseEvent event)
    {
        showTab(tabMain);
    }





    @FXML
    void tfNavSearchKeyTyped(KeyEvent event)
    {

    }
}