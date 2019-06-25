package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import DataAccessObjects.AgencyManager;
import DataAccessObjects.AgentManager;
import DomainEntities.Agency;
import DomainEntities.Agent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javax.swing.*;

public class AgencyController {

    //form level variables
    private String formMode;  //variable to track if form is in add/edit/navigate mode
    private ArrayList<Agency> navTableArrayList;  //array list used to populate the navigation pane table
    private Agency currentAgency;  //the current agent selected and shown in the detail view


    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TabPane tabpaneMain;
    @FXML private Tab tabNav;
    @FXML private TableView<Agency> tvNavTable;
    @FXML private TableColumn<Agency, Integer> colAgencyId;
    @FXML private TableColumn<Agency, String> colAgncyAddress;
    @FXML private TableColumn<Agency, String> colAgncyCity;
    @FXML private TableColumn<Agency, String> colAgncyProv;
    @FXML private TableColumn<Agency, String> colAgncyPostal;
    @FXML private TableColumn<Agency, String> colAgncyCountry;
    @FXML private TableColumn<Agency, String> colAgncyPhone;
    @FXML private TableColumn<Agency, String> colAgncyFax;
    @FXML private Button btnNavAdd;
    @FXML private Button btnNavEdit;
    @FXML private TextField tfNavSearch;
    @FXML private Tab tabDetail;
    @FXML private GridPane grPane;
    @FXML private ColumnConstraints gpDetails;
    @FXML private TextField tfAgencyId;
    @FXML private TextField tfAgncyAddress;
    @FXML private TextField tfAgncyCity;
    @FXML private TextField tfAgncyProv;
    @FXML private TextField tfAgncyPostal;
    @FXML private TextField tfAgncyCountry;
    @FXML private TextField tfAgncyPhone;
    @FXML private TextField tfAgncyFax;
    @FXML private Label lbErrorAgncyAddress;
    @FXML private Label lbErrorAgncyCity;
    @FXML private Label lbErrorAgncyProv;
    @FXML private Label lbErrorAgncyPostal;
    @FXML private Label lbErrorAgncyCountry;
    @FXML private Label lbErrorAgncyPhone;
    @FXML private Label lbErrorAgncyFax;
    @FXML private Button btnDetailClose;
    @FXML private Button btnDetailSaveClose;
    @FXML private Button btnDetailSave;
    @FXML private Button btnDetailUndo;

    /****************************************************************************
     *
     *                              BUTTON EVENT HANDLERS
     *
     ****************************************************************************/

    @FXML
    void btnDetailCloseClicked(MouseEvent event) {

        //clear detail form inputs and set currentAgent object to null
        clearDetailForm();
        currentAgency = null;

        //display navigation pane
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabNav);
    }

    @FXML
    void btnDetailSaveClicked(MouseEvent event) {
        saveItem();
    }

    @FXML
    void btnDetailSaveCloseClicked(MouseEvent event) {

        saveItem();

        //display the navigation pane
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabNav);

        //refresh the data in the NavTable
        setNavigateMode();
    }

    @FXML
    void btnDetailUndoClicked(MouseEvent event) {
        //refresh all input controls with "currentAgency" data
        loadItemDetail(currentAgency);
    }

    @FXML
    void btnNavAddClick(MouseEvent event) {
        setNewMode();
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);
    }

    @FXML
    void btnNavEditClick(MouseEvent event) {
        currentAgency =  tvNavTable.getSelectionModel().getSelectedItem();
        setEditMode(currentAgency);
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);
    }

    @FXML
    void tfNavSearchKeyReleased(KeyEvent event) {
        System.out.println("pressed");

        String strFilter = tfNavSearch.getText();

        List<Agency> filteredList = navTableArrayList.stream()
                .filter(d -> d.toString().contains(strFilter))
                .collect(Collectors.toList());

        System.out.println(filteredList);

        ObservableList<Agency> data = FXCollections.observableArrayList(filteredList);
        tvNavTable.setItems(data);

    }


    /****************************************************************************
     *
     *                              FORM SETUP METHODS
     *
     *
     * **************************************************************************/


    @FXML
    void initialize() {
        assert tabpaneMain != null : "fx:id=\"tabpaneMain\" was not injected: check your FXML file 'agency.fxml'.";
        assert tabNav != null : "fx:id=\"tabNav\" was not injected: check your FXML file 'agency.fxml'.";
        assert tvNavTable != null : "fx:id=\"tvNavTable\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgncyAddress != null : "fx:id=\"colAgncyAddress\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgncyCity != null : "fx:id=\"colAgncyCity\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgncyProv != null : "fx:id=\"colAgncyProv\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgncyPostal != null : "fx:id=\"colAgncyPostal\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgncyCountry != null : "fx:id=\"colAgncyCountry\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgncyPhone != null : "fx:id=\"colAgncyPhone\" was not injected: check your FXML file 'agency.fxml'.";
        assert colAgncyFax != null : "fx:id=\"colAgncyFax\" was not injected: check your FXML file 'agency.fxml'.";
        assert btnNavAdd != null : "fx:id=\"btnNavAdd\" was not injected: check your FXML file 'agency.fxml'.";
        assert btnNavEdit != null : "fx:id=\"btnNavEdit\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfNavSearch != null : "fx:id=\"tfNavSearch\" was not injected: check your FXML file 'agency.fxml'.";
        assert tabDetail != null : "fx:id=\"tabDetail\" was not injected: check your FXML file 'agency.fxml'.";
        assert grPane != null : "fx:id=\"grPane\" was not injected: check your FXML file 'agency.fxml'.";
        assert gpDetails != null : "fx:id=\"gpDetails\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgncyAddress != null : "fx:id=\"tfAgncyAddress\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgncyCity != null : "fx:id=\"tfAgncyCity\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgncyProv != null : "fx:id=\"tfAgncyProv\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgncyPostal != null : "fx:id=\"tfAgncyPostal\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgncyCountry != null : "fx:id=\"tfAgncyCountry\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgncyPhone != null : "fx:id=\"tfAgncyPhone\" was not injected: check your FXML file 'agency.fxml'.";
        assert tfAgncyFax != null : "fx:id=\"tfAgncyFax\" was not injected: check your FXML file 'agency.fxml'.";
        assert lbErrorAgncyAddress != null : "fx:id=\"lbErrorAgncyAddress\" was not injected: check your FXML file 'agency.fxml'.";
        assert lbErrorAgncyCity != null : "fx:id=\"lbErrorAgncyCity\" was not injected: check your FXML file 'agency.fxml'.";
        assert lbErrorAgncyProv != null : "fx:id=\"lbErrorAgncyProv\" was not injected: check your FXML file 'agency.fxml'.";
        assert lbErrorAgncyPostal != null : "fx:id=\"lbErrorAgncyPostal\" was not injected: check your FXML file 'agency.fxml'.";
        assert lbErrorAgncyCountry != null : "fx:id=\"lbErrorAgncyCountry\" was not injected: check your FXML file 'agency.fxml'.";
        assert lbErrorAgncyPhone != null : "fx:id=\"lbErrorAgncyPhone\" was not injected: check your FXML file 'agency.fxml'.";
        assert lbErrorAgncyFax != null : "fx:id=\"lbErrorAgncyFax\" was not injected: check your FXML file 'agency.fxml'.";
        assert btnDetailClose != null : "fx:id=\"btnDetailClose\" was not injected: check your FXML file 'agency.fxml'.";
        assert btnDetailSaveClose != null : "fx:id=\"btnDetailSaveClose\" was not injected: check your FXML file 'agency.fxml'.";
        assert btnDetailSave != null : "fx:id=\"btnDetailSave\" was not injected: check your FXML file 'agency.fxml'.";
        assert btnDetailUndo != null : "fx:id=\"btnDetailUndo\" was not injected: check your FXML file 'agency.fxml'.";

        //setup up table cell factories
        colAgencyId.setCellValueFactory(cellData -> cellData.getValue().agencyIdProperty().asObject());
        colAgncyAddress.setCellValueFactory(cellData -> cellData.getValue().agncyAddressProperty());
        colAgncyCity.setCellValueFactory(cellData -> cellData.getValue().agncyCityProperty());
        colAgncyProv.setCellValueFactory(cellData -> cellData.getValue().agncyProvProperty());
        colAgncyPostal.setCellValueFactory(cellData -> cellData.getValue().agncyPostalProperty());
        colAgncyCountry.setCellValueFactory(cellData -> cellData.getValue().agncyCountryProperty());
        colAgncyPhone.setCellValueFactory(cellData -> cellData.getValue().agncyPhoneProperty());
        colAgncyFax.setCellValueFactory(cellData -> cellData.getValue().agncyFaxProperty());

        setNavigateMode();

        //set event handlers

        //item table - double click
        tvNavTable.setOnMouseClicked( event -> {
            if( event.getClickCount() == 2 ) {
                currentAgency = tvNavTable.getSelectionModel().getSelectedItem();
                setEditMode(currentAgency);
            }});

        setupFocusListenerTfAgncyAddress();  //First name is text
        setupFocusListenerTfAgncyPhone();  //phone is (###) ###-#### format
        setupFocusListenerTfAgncyFax();  //phone is (###) ###-#### format
    }

    //*******************************     NAVIGATE MODE      ********************************//

    private void setNavigateMode() {
        formMode = "Navigate";
        navTableArrayList = AgencyManager.getAllAgencies();
        ObservableList<Agency> data = FXCollections.observableArrayList(navTableArrayList);
        tvNavTable.setItems(data);
        SelectionModel<Agency> selectionModel = tvNavTable.getSelectionModel();
        selectionModel.selectFirst();
    }

    //*******************************     EDIT MODE      ********************************//
    private void setEditMode(Agency agncy) {
        formMode = "Edit";
        SelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);

        tfAgencyId.setVisible(true);
        clearDetailErrorMessages();
        loadItemDetail(agncy);
    }

    //*******************************     NEW/ADD ITEM MODE      ********************************//

    private void setNewMode() {
        formMode = "New";
        System.out.println(formMode);
        tfAgencyId.setVisible(false);
        clearDetailErrorMessages();
        clearDetailForm();
    }

    /*******************************  CLEAR ERROR MESSAGES  *****************************/

    //enumerate all controls in grid view and hide those with styleclass "FieldErrorMessage"
    private void clearDetailErrorMessages() {

        //get list of all controls in the grid pane
        ObservableList<Node> nodeList = grPane.getChildren();

        for (int i = 0; i < nodeList.size(); i++) {
            //get the control and its style class
            Node n = nodeList.get(i);
            String controlStyleClass = n.getStyleClass().toString();
            //hide all error message controls that match the styleclass
            if (controlStyleClass.contains("DetailError")) {
                n.setVisible(false);
            }
        }
    }


    /*******************************     CLEAR ALL INPUT FIELDS      ********************************/
    private void clearDetailForm() {
        //get list of all controls in the grid pane
        ObservableList<Node> nodeList = grPane.getChildren();

        //enumerate all controls and set style/properties for textfields
        for (int i = 0; i < nodeList.size(); i++) {
            //get the control
            Node n = nodeList.get(i);
            if (n instanceof TextField)  //test if node is a text field
            {
                //set style for textfield nodes
                ((TextField) n).setText("");
            }
        }
    }

    /*******************************************************************************
     *
     *                           DATA LOAD/EDIT/SAVE METHODS
     *
     *******************************************************************************/

    private void loadItemDetail(Agency a) {

        //populate controls with data
        tfAgencyId.setText(String.valueOf(a.getAgencyId()));
        tfAgncyAddress.setText(a.getAgncyAddress());
        tfAgncyCity.setText(a.getAgncyCity());
        tfAgncyProv.setText(a.getAgncyProv());
        tfAgncyPostal.setText(a.getAgncyPostal());
        tfAgncyCountry.setText(a.getAgncyCountry());
        tfAgncyPhone.setText(a.getAgncyPhone());
        tfAgncyFax.setText(a.getAgncyFax());
    }



    /*******************************    SAVE ITEM      ********************************/
    private void saveItem() {

        //check if any errors messages are displayed
        int errorCount = 0;

        //get list of all controls in the grid pane
        ObservableList<Node> nodeList = grPane.getChildren();

        for (int i = 0; i < nodeList.size(); i++) {
            //get the control and its style class
            Node n = nodeList.get(i);
            String controlStyleClass = n.getStyleClass().toString();
            if (controlStyleClass.contains("DetailError")) {
                if (n.isVisible()) {
                    errorCount += 1;
                };
            }
        }
        System.out.println("Error count: " + errorCount);

        //test if form is in new mode, if true set ID = 0 as temp value
        if (errorCount == 0) {
            if (formMode.equals("New")) {

                Agency agncyChanged = new Agency(
                        0,
                        tfAgncyAddress.getText(),
                        tfAgncyCity.getText(),
                        tfAgncyProv.getText(),
                        tfAgncyPostal.getText(),
                        tfAgncyCountry.getText(),
                        tfAgncyPhone.getText(),
                        tfAgncyFax.getText());
                try {
                    //insert item into database

                    int newItemId = AgencyManager.addAgency(agncyChanged);

                    if (newItemId != 0) {
                        JOptionPane.showMessageDialog(null, "New item created!",
                                "", JOptionPane.INFORMATION_MESSAGE);
                        currentAgency = AgencyManager.getAgencyById(newItemId);
                        setEditMode(currentAgency);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cannot save item.  Please correct input errors and try saving again.",
                                "Warning", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (formMode.equals("Edit")) {
                Agency agncyChanged = new Agency(
                        Integer.parseInt(tfAgencyId.getText()),
                        tfAgncyAddress.getText(),
                        tfAgncyCity.getText(),
                        tfAgncyProv.getText(),
                        tfAgncyPostal.getText(),
                        tfAgncyCountry.getText(),
                        tfAgncyPhone.getText(),
                        tfAgncyFax.getText());



                try {
                    if (AgencyManager.updateAgency(agncyChanged)) {
                        JOptionPane.showMessageDialog(null, "Update successful.",
                                "", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**********************************************************************************************
     *
     *
     *                              INPUT CONTROL VALIDATION EVENT LISTENERS
     *
     *
     ***********************************************************************************************/

    public void setupFocusListenerTfAgncyAddress() {

        tfAgncyAddress.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfText(tfAgncyAddress))
                    {
                        //else hide error message
                        lbErrorAgncyAddress.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgncyAddress.setVisible(true);
                        lbErrorAgncyAddress.setText("First name is required.");
                        lbErrorAgncyAddress.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

    public void setupFocusListenerTfAgncyPhone() {

        tfAgncyPhone.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfPhoneNumber(tfAgncyPhone))
                    {
                        //else hide error message
                        lbErrorAgncyPhone.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgncyPhone.setVisible(true);
                        lbErrorAgncyPhone.setText("Valid format (555) 555-5555 required");
                        lbErrorAgncyPhone.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

    public void setupFocusListenerTfAgncyFax() {

        tfAgncyFax.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfPhoneNumber(tfAgncyFax))
                    {
                        //else hide error message
                        lbErrorAgncyFax.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgncyFax.setVisible(true);
                        lbErrorAgncyFax.setText("Valid format (555) 555-5555 required");
                        lbErrorAgncyFax.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }
}

