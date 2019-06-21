/*
Purpose: Controller Class for packages and their Product-Supplier combinations
Author:  Hoora
Date: June, 2019


<!--
    Purpose: view for packages and their Product-Supplier combinations
    Author:  Hoora
    Date: May-June, 2019
-->

 */

package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import DataAccessObjects.PackageManager;
import DataAccessObjects.PackageProductSupplierManager;
import DomainEntities.PackageProductSupplier;
import DomainEntities.Package;

import DomainEntities.ProductSupplierNames;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class PackageController
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane tabPaneMain;


    //***************************************** Main Tab*********************************************
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


    //***************************************** Edit Tab*********************************************
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
    private DatePicker pkgStartDateEdit;

    @FXML
    private DatePicker pkgEndDateEdit;

    @FXML
    private CheckBox pkgActiveEdit;


    //***************************************** Add Tab**********************************************
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
    private DatePicker pkgStartDateAdd;

    @FXML
    private DatePicker pkgEndDateAdd;

    @FXML
    private CheckBox pkgActiveAdd;


    //***************************************** Add/Edit Tab*****************************************
    @FXML
    private Tab tabAddEdit;

    @FXML
    private ListView<ProductSupplierNames> lvPsblPrdSpl;

    @FXML
    private Button btnAddEditAdd;

    @FXML
    private Button btnAddEditAddAll;

    @FXML
    private ListView<PackageProductSupplier> lvCrntPrdSpl;

    @FXML
    private Button btnAddEditDelete;

    @FXML
    private Button btnAddEditDeleteAll;

    @FXML
    private Button btnAddEditBack;


    //***************************************** Main Tab*********************************************
    // class-level variables:

    Package curntPkg = new Package(); // current (selected) package in the package scene


    @FXML
    void initialize()
    {

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

        selectToEdit();

        firstPackage();
    }


    // a method to load all packages from database to the table view
    private void loadPackages()
    {
        ObservableList<Package> packages;
        packages = PackageManager.getAllPackages();
        tvPackages.setItems(packages);
    }


    // a method to pre-select the first package in the table,
    // show it on the Edit tab and show its product-supplier combinations on list view
    private void firstPackage()
    {
        tvPackages.getSelectionModel().select(0);
        Package pkg = tvPackages.getSelectionModel().getSelectedItem();
        loadPackage(pkg);
    }


    // a method to show the selected package on the Edit tab,
    // show its product-supplier combinations on list view and
    // show its product-supplier combinations on Add/Edit tab
    private void loadPackage(Package pkg)
    {
//        Package pkg = tvPackages.getSelectionModel().getSelectedItem();
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


        setListView(lvProductsSuppliers, pkg);


        setListView(lvCrntPrdSpl, pkg);


        setListViewOther(lvPsblPrdSpl, pkg);
    }


    // a method to set the items for a list view based on a given packageId
    private void setListView(ListView lv, Package pkg)
    {
        lv.setItems(PackageProductSupplierManager.getPrdSplByPkgId(pkg.getPackageId()));
    }


    // a method to set the items for a list view based on a given packageId
    // the items on the list view do not belog to the package
    private void setListViewOther(ListView lv, Package pkg)
    {
        lv.setItems(PackageProductSupplierManager.getPrdSplNotForPkgId(pkg.getPackageId()));
    }


    // a method to show the selected package on the Edit tab
    // and set double click settings
    private void selectToEdit()
    {
        tvPackages.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                curntPkg = currentPackage();
                loadPackage(curntPkg);
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
        Package Pkg = tvPackages.getSelectionModel().getSelectedItem();
        return Pkg;
    }


    // a method to show different tabs
    private void showTab(Tab tab)
    {
        SelectionModel<Tab> selectionModel = tabPaneMain.getSelectionModel();
        selectionModel.select(tab);
    }


    // search method
    @FXML
    void tfSearchTyped(KeyEvent event)
    {
        System.out.println("pressed");

        String stringFilter = tfSearch.getText();

        ObservableList<Package> packages;
        packages = PackageManager.getAllPackages();

        List<Package> filteredList = packages.stream()
                .filter(d -> d.toString().contains(stringFilter))
                .collect(Collectors.toList());

        System.out.println(filteredList);

        ObservableList<Package> data = FXCollections.observableArrayList(filteredList);
        tvPackages.setItems(data);
    }


    //*********************************************Edit Tab**********************************************
    // an event handler on the Package Name text field so after filling it, the save buttons are disabled
    // and message beside it will disappear
    @FXML
    void pkgNameEditTyped(KeyEvent event)
    {
        boolean isNameEmpty = checkpkgNameEdit();
        boolean isPriceEmpty = checkpkgPriceEdit();
        setButtonsEdit(isNameEmpty, isPriceEmpty, errMsgEdit);

        if(isNameEmpty == false)
        {
            lbErPkgNameEdit.setVisible(false);
        }
        else
        {
            lbErPkgNameEdit.setVisible(true);
        }
    }


    // an event handler on the Package Base Price text field so after filling it, the save buttons are disabled
    // and message beside it will disappear
    @FXML
    void pkgBasePriceEditTyped(KeyEvent event)
    {
        boolean isNameEmpty = checkpkgNameEdit();
        boolean isPriceEmpty = checkpkgPriceEdit();
        setButtonsEdit(isNameEmpty, isPriceEmpty, errMsgEdit);

        if(isPriceEmpty == false)
        {
            lbErPkgBPriceEdit.setVisible(false);
        }
        else
        {
            lbErPkgBPriceEdit.setVisible(true);
        }
    }


    // a method to check if the package name field is empty or not
    private boolean checkpkgNameEdit()
    {
        boolean isEmpty = false;

        if (pkgNameEdit.getText().equals(""))
        {
            isEmpty = true;
        }

        return isEmpty;
    }

    // a method to check if the package base price field is empty or not
    private boolean checkpkgPriceEdit()
    {
        boolean isEmpty = false;

        if (pkgBasePriceEdit.getText().equals(""))
        {
            isEmpty = true;
        }

        return isEmpty;
    }


    // set the save buttons disabled or enabled based on the required fields content(empty or not)
    private void setButtonsEdit(boolean isNameEmpty, boolean isPriceEmpty, boolean errMsg) {
        if (isNameEmpty == false && isPriceEmpty == false && errMsg == false)
        {
            btnEditSaveBack.setDisable(false);
            btnEditSaveEdit.setDisable(false);
        }
        else
        {
            btnEditSaveBack.setDisable(true);
            btnEditSaveEdit.setDisable(true);
        }
    }


    boolean errMsgEdit = false;
    // an event handler on the Package start date datepicker field so after selecting a date,
    // compare it with the end date and show proper error message
    @FXML
    void pkgStartDateEditClicked(MouseEvent event)
    {
        boolean isCorrect = pkgDatesEditCheck();
        if (isCorrect == false)
        {
            lbErPkgSDatEdit.setText("The start date should be before the end date!");
            lbErPkgSDatEdit.setVisible(true);
            errMsgEdit = true;

            boolean isNameEmpty = checkpkgNameEdit();
            boolean isPriceEmpty = checkpkgPriceEdit();
            setButtonsEdit(isNameEmpty, isPriceEmpty, errMsgEdit);
        }
        else
        {
            lbErPkgSDatEdit.setVisible(false);
            errMsgEdit = false;

            boolean isNameEmpty = checkpkgNameEdit();
            boolean isPriceEmpty = checkpkgPriceEdit();
            setButtonsEdit(isNameEmpty, isPriceEmpty, errMsgEdit);
        }
    }


    // an event handler on the Package end date datepicker field so after selecting a date,
    // compare it with the start date and show proper error message
    @FXML
    void pkgEndDateEditClicked(MouseEvent event)
    {
        boolean isCorrect = pkgDatesEditCheck();
        if (isCorrect == false)
        {
            lbErPkgEDatEdit.setText("The end date should be after the start date!");
            lbErPkgEDatEdit.setVisible(true);
            boolean errMsg = true;

            boolean isNameEmpty = checkpkgNameEdit();
            boolean isPriceEmpty = checkpkgPriceEdit();
            setButtonsEdit(isNameEmpty, isPriceEmpty, errMsgEdit);
        }
        else
        {
            lbErPkgEDatEdit.setVisible(false);
            boolean errMsg = false;

            boolean isNameEmpty = checkpkgNameEdit();
            boolean isPriceEmpty = checkpkgPriceEdit();
            setButtonsEdit(isNameEmpty, isPriceEmpty, errMsgEdit);
        }
    }

    // a method to compare the start date and end date
    private boolean pkgDatesEditCheck()
    {
        boolean isCorrect = true;
        LocalDate strDate = null;
        LocalDate endDate = null;

        if (pkgStartDateEdit.getValue()!=null)
            strDate = pkgStartDateEdit.getValue();

        if (pkgEndDateEdit.getValue()!=null)
            endDate = pkgEndDateEdit.getValue();

        if (strDate !=null && endDate != null)
            if(strDate.isAfter(endDate))
                isCorrect = false;

        return isCorrect;
    }


    // a method to create a package from text fields on Edit tab
    private Package readEditedPackage()
    {
        Package pkg = new Package();

        pkg.setPkgName(pkgNameEdit.getText());

        if (pkgStartDateEdit.getValue()!=null)
            pkg.setPkgStartDate(pkgStartDateEdit.getValue());
        else
            pkg.setPkgStartDate(null);

        if (pkgEndDateEdit.getValue()!=null)
            pkg.setPkgEndDate(pkgEndDateEdit.getValue());
        else
            pkg.setPkgEndDate(null);

        if (!pkgDescEdit.getText().isEmpty())
            pkg.setPkgDesc(pkgDescEdit.getText());
        else
            pkg.setPkgDesc("");

        pkg.setPkgBasePrice(Double.parseDouble(pkgBasePriceEdit.getText()));

        pkg.setPkgAgencyCommission(Double.parseDouble(pkgAgencyCommissionEdit.getText()));

        if (pkgActiveEdit.isSelected())
            pkg.setActive(true);
        else
            pkg.setActive(false);
        return pkg;
    }


    // Back button
    @FXML
    void btnEditBackClicked(MouseEvent event)
    {
        showTab(tabMain);
        firstPackage();
    }


    // Save & Back button
    @FXML
    void btnEditSaveBackClicked(MouseEvent event)
    {
        Package oldPkg = curntPkg;
        Package newPkg = readEditedPackage();
        newPkg.setPackageId(oldPkg.getPackageId());
        PackageManager.updatePackage(newPkg, oldPkg);
        loadPackages();
        firstPackage();
        showTab(tabMain);
    }


    // Save & Edit Product-Supplier button
    @FXML
    void btnEditSaveEditClicked(MouseEvent event)
    {
        Package oldPkg = curntPkg;
        Package newPkg = readEditedPackage();
        newPkg.setPackageId(oldPkg.getPackageId());
        PackageManager.updatePackage(newPkg, oldPkg);
        loadPackage(newPkg);
        loadPackages();
        showTab(tabAddEdit);
    }


    // Undo changes button
    @FXML
    void btnEditUndoClicked(MouseEvent event)
    {
        Package Pkg = curntPkg;
        loadPackage(Pkg);
        btnEditSaveBack.setDisable(false);
        btnEditSaveEdit.setDisable(false);

        lbErPkgNameEdit.setVisible(false);
        lbErPkgBPriceEdit.setVisible(false);
        lbErPkgSDatEdit.setVisible(false);
        lbErPkgEDatEdit.setVisible(false);
    }


    //**********************************************Add Tab**********************************************
    // an event handler on the Package Name text field so after filling it, the save buttons are disabled
    // and message beside it will disappear
    @FXML
    void pkgNameAddTyped(KeyEvent event)
    {
        boolean isNameEmpty = checkpkgNameAdd();
        boolean isPriceEmpty = checkpkgPriceAdd();
        setButtonsAdd(isNameEmpty, isPriceEmpty, errMsgAdd);

        if(isNameEmpty == false)
        {
            lbErPkgNameAdd.setVisible(false);
        }
        else
        {
            lbErPkgNameAdd.setVisible(true);
        }
    }


    // an event handler on the Package Base Price text field so after filling it, the save buttons are no longer disabled
    @FXML
    void pkgBasePriceAddTyped(KeyEvent event)
    {
        boolean isNameEmpty = checkpkgNameAdd();
        boolean isPriceEmpty = checkpkgPriceAdd();
        setButtonsAdd(isNameEmpty, isPriceEmpty, errMsgAdd);

        if (isNameEmpty == false && isPriceEmpty == false)
        {
            btnAddSaveBack.setDisable(false);
            btnAddSaveAdd.setDisable(false);
        }
        else
        {
            btnAddSaveBack.setDisable(true);
            btnAddSaveAdd.setDisable(true);
        }
        if(isPriceEmpty == false)
        {
            lbErPkgBPriceAdd.setVisible(false);
        }
        else
        {
            lbErPkgBPriceAdd.setVisible(true);
        }
    }


    // a method to check if the package name field is empty or not
    private boolean checkpkgNameAdd()
    {
        boolean isEmpty = false;

        if (pkgNameAdd.getText().isEmpty())
        {
            isEmpty = true;
        }

        return isEmpty;
    }


    // a method to check if the package base price field is empty or not
    private boolean checkpkgPriceAdd()
    {
        boolean isEmpty = false;

        if (pkgBasePriceAdd.getText().equals(""))
        {
            isEmpty = true;
        }

        return isEmpty;
    }


    // set the save buttons disabled or enabled based on the required fields content(empty or not)
    private void setButtonsAdd(boolean isNameEmpty, boolean isPriceEmpty, boolean errMsg) {
        if (isNameEmpty == false && isPriceEmpty == false && errMsg == false)
        {
            btnAddSaveBack.setDisable(false);
            btnAddSaveAdd.setDisable(false);
        }
        else
        {
            btnAddSaveBack.setDisable(true);
            btnAddSaveAdd.setDisable(true);
        }
    }

    boolean errMsgAdd = false;
    // an event handler on the Package start date datepicker field so after selecting a date,
    // compare it with the end date and show proper error message
    @FXML
    void pkgStartDateAddClicked(MouseEvent event)
    {
        boolean isCorrect = pkgDatesAddCheck();
        if (isCorrect == false)
        {
            lbErPkgSDatAdd.setText("The start date should be before the end date!");
            lbErPkgSDatAdd.setVisible(true);
            boolean errMsg = true;

            boolean isNameEmpty = checkpkgNameAdd();
            boolean isPriceEmpty = checkpkgPriceAdd();
            setButtonsAdd(isNameEmpty, isPriceEmpty, errMsgAdd);
        }
        else
        {
            lbErPkgSDatAdd.setVisible(false);
            boolean errMsg = false;

            boolean isNameEmpty = checkpkgNameAdd();
            boolean isPriceEmpty = checkpkgPriceAdd();
            setButtonsAdd(isNameEmpty, isPriceEmpty, errMsgAdd);
        }
    }


    // an event handler on the Package end date datepicker field so after selecting a date,
    // compare it with the start date and show proper error message
    @FXML
    void pkgEndDateAddClicked(MouseEvent event)
    {
        boolean isCorrect = pkgDatesAddCheck();
        if (isCorrect == false)
        {
            lbErPkgEDatAdd.setText("The end date should be after the start date!");
            lbErPkgEDatAdd.setVisible(true);
            boolean errMsg = true;

            boolean isNameEmpty = checkpkgNameAdd();
            boolean isPriceEmpty = checkpkgPriceAdd();
            setButtonsAdd(isNameEmpty, isPriceEmpty, errMsgAdd);
        }
        else
        {
            lbErPkgEDatAdd.setVisible(false);
            boolean errMsg = false;

            boolean isNameEmpty = checkpkgNameAdd();
            boolean isPriceEmpty = checkpkgPriceAdd();
            setButtonsAdd(isNameEmpty, isPriceEmpty, errMsgAdd);
        }
    }


    // a method to compare the start date and end date
    private boolean pkgDatesAddCheck()
    {
        boolean isCorrect = true;
        LocalDate strDate = null;
        LocalDate endDate = null;

        if (pkgStartDateAdd.getValue()!=null)
            strDate = pkgStartDateAdd.getValue();

        if (pkgEndDateAdd.getValue()!=null)
            endDate = pkgEndDateAdd.getValue();

        if (strDate !=null && endDate != null)
            if(strDate.isAfter(endDate))
                isCorrect = false;

        return isCorrect;
    }


    // a method to create a package from text fields on Add tab
    private Package readAddedPackage()
    {
        Package pkg = new Package();

        pkg.setPkgName(pkgNameAdd.getText());

        if (pkgStartDateAdd.getValue()!=null)
            pkg.setPkgStartDate(pkgStartDateAdd.getValue());
        else
            pkg.setPkgStartDate(null);

        if (pkgEndDateAdd.getValue()!=null)
            pkg.setPkgEndDate(pkgEndDateAdd.getValue());
        else
            pkg.setPkgEndDate(null);

        if (!pkgDescAdd.getText().isEmpty())
            pkg.setPkgDesc(pkgDescAdd.getText());
        else
            pkg.setPkgDesc("");

        pkg.setPkgBasePrice(Double.parseDouble(pkgBasePriceAdd.getText()));

        pkg.setPkgAgencyCommission(Double.parseDouble(pkgAgencyCommissionAdd.getText()));

        if (pkgActiveAdd.isSelected())
            pkg.setActive(true);
        else
            pkg.setActive(false);
        return pkg;
    }


    // Back button
    @FXML
    void btnAddBackClicked(MouseEvent event)
    {
        firstPackage();
        showTab(tabMain);
    }


    // Save & Back button
    @FXML
    void btnAddSaveBackClicked(MouseEvent event)
    {
        Package pkg = readAddedPackage();
        PackageManager.insertPackage(pkg);
        firstPackage();
        loadPackages();
        resetAddTab();
        showTab(tabMain);
    }


    // Save & Add Product-Supplier button
    @FXML
    void btnAddSaveAddClicked(MouseEvent event)
    {
        curntPkg = readAddedPackage();
        int id = PackageManager.insertPackage(curntPkg);
        curntPkg.setPackageId(id);
        loadPackage(curntPkg);
        loadPackages();
        resetAddTab();
        showTab(tabAddEdit);
    }


    // Reset button
    @FXML
    void btnAddResetClicked(MouseEvent event)
    {
        resetAddTab();
    }


    // a method to clear the fields on Add tab and disable the save buttons and show the messages
    private void resetAddTab()
    {
        pkgNameAdd.setText("");
        pkgStartDateAdd.setValue(null);
        pkgEndDateAdd.setValue(null);
        pkgDescAdd.setText("");
        pkgBasePriceAdd.setText("");
        pkgAgencyCommissionAdd.setText("");
        pkgActiveAdd.setSelected(true);

        btnAddSaveBack.setDisable(true);
        btnAddSaveAdd.setDisable(true);

        lbErPkgNameAdd.setVisible(true);
        lbErPkgBPriceAdd.setVisible(true);
        lbErPkgSDatAdd.setVisible(false);
        lbErPkgEDatAdd.setVisible(false);
    }


    //**************************************** Add/ Edit Tab*********************************************
    // Back button
    @FXML
    void btnAddEditBackClicked(MouseEvent event)
    {
        showTab(tabMain);
        firstPackage();
    }


    // add a set of product-supplier
    @FXML
    void btnAddEditAddClicked(MouseEvent event)
    {
        ProductSupplierNames prdSpl = lvPsblPrdSpl.getSelectionModel().getSelectedItem();
        int pkgId = curntPkg.getPackageId();
        int prdSplId = prdSpl.getProductSupplierId();
        PackageProductSupplierManager.insertPkgPrdSpl(pkgId, prdSplId);

        setListView(lvCrntPrdSpl, curntPkg);
        setListViewOther(lvPsblPrdSpl, curntPkg);
        loadPackage(curntPkg);
    }


    // add all sets of possible product-supplier
    @FXML
    void btnAddEditAddAllClicked(MouseEvent event)
    {
        int pkgId = curntPkg.getPackageId();

        ObservableList<ProductSupplierNames> PrdSpls =
                PackageProductSupplierManager.getPrdSplNotForPkgId(curntPkg.getPackageId());
        int listLength = PrdSpls.size();

        for (int i=0; i<listLength;i++)
        {
            lvPsblPrdSpl.getSelectionModel().select(i);
            ProductSupplierNames prdSpl = lvPsblPrdSpl.getSelectionModel().getSelectedItem();
            int prdSplId = prdSpl.getProductSupplierId();
            PackageProductSupplierManager.insertPkgPrdSpl(pkgId, prdSplId);
        }

        setListView(lvCrntPrdSpl, curntPkg);
        setListViewOther(lvPsblPrdSpl, curntPkg);
        loadPackage(curntPkg);
    }


    // delete a related set of product-supplier
    @FXML
    void btnAddEditDeleteClicked(MouseEvent event)
    {
        PackageProductSupplier pk = lvCrntPrdSpl.getSelectionModel().getSelectedItem();
        int pkId = pk.getPackageId();
        int psId = pk.getProductSupplierId();
        PackageProductSupplierManager.deletePkgPrdSpl(pkId, psId);

        setListView(lvCrntPrdSpl, curntPkg);
        setListViewOther(lvPsblPrdSpl, curntPkg);
        loadPackage(curntPkg);
    }


    // delete all related sets of product-supplier
    @FXML
    void btnAddEditDeleteAllClicked(MouseEvent event)
    {
        PackageProductSupplierManager.deleteAllPkgPrdSpl(curntPkg.getPackageId());

        setListView(lvCrntPrdSpl, curntPkg);
        setListViewOther(lvPsblPrdSpl, curntPkg);
        loadPackage(curntPkg);
    }
}