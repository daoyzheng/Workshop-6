package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import DataAccessObjects.AgentManager;
import DataAccessObjects.CustomerManager;
import DomainEntities.Agent;
import DomainEntities.Customer;
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

public class CustomerController {

    //form level variables
    private String formMode;  //variable to track if form is in add/edit/navigate mode
    private ArrayList<Customer> navTableArrayList;  //array list used to populate the navigation pane table
    private Customer currentCustomer;  //the current agent selected and shown in the detail view

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TabPane tabpaneMain;
    @FXML private Tab tabNav;
    @FXML private TableView<Customer> tvNavTable;
    @FXML private TableColumn<Customer, Integer> colCustomerId;
    @FXML private TableColumn<Customer, String> colCustFirstName;
    @FXML private TableColumn<Customer, String> colCustLastName;
    @FXML private TableColumn<Customer, String> colCustAddress;
    @FXML private TableColumn<Customer, String> colCustCity;
    @FXML private TableColumn<Customer, String> colCustProv;
    @FXML private TableColumn<Customer, String> colCustPostal;
    @FXML private TableColumn<Customer, String> colCustCountry;
    @FXML private TableColumn<Customer, String> colCustHomePhone;
    @FXML private TableColumn<Customer, String> colCustBusPhone;
    @FXML private TableColumn<Customer, String> colCustEmail;
    @FXML private TableColumn<Customer, String> colCustUserName;
    @FXML private TableColumn<Customer, String> colCustPassword;
    @FXML private TableColumn<Customer, String> colAgentId;
    @FXML private Button btnNavAdd;
    @FXML private Button btnNavEdit;
    @FXML private TextField tfNavSearch;
    @FXML private Tab tabDetail;
    @FXML private GridPane grPane;
    @FXML private ColumnConstraints gpDetails;
    @FXML private TextField tfCustomerId;
    @FXML private TextField tfFname;
    @FXML private TextField tfLname;
    @FXML private TextField tfAddress;
    @FXML private TextField tfCity;
    @FXML private TextField tfProv;
    @FXML private TextField tfPostal;
    @FXML private TextField tfCountry;
    @FXML private TextField tfPhoneHome;
    @FXML private TextField tfPhoneBus;
    @FXML private TextField tfEmail;
    @FXML private TextField tfUsername;
    @FXML private TextField tfPassword;
    @FXML private ComboBox<Agent> cboAgentId;
    @FXML private Label lbErrorCustFirstName;
    @FXML private Label lbErrorCustLastName;
    @FXML private Label lbErrorCustAddress;
    @FXML private Label lbErrorCustCity;
    @FXML private Label lbErrorCustProv;
    @FXML private Label lbErrorCustPostal;
    @FXML private Label lbErrorCustCountry;
    @FXML private Label lbErrorCustHomePhone;
    @FXML private Label lbErrorCustBusPhone;
    @FXML private Label lbErrorCustEmail;
    @FXML private Label lbErrorCustUsername;
    @FXML private Label lbErrorCustPassword;
    @FXML private Label lbErrorCustAgentId;
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
        currentCustomer = null;

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
        //refresh all input controls with "currentAgent" data
        loadItemDetail(currentCustomer);
    }

    @FXML
    void btnNavAddClick(MouseEvent event) {
        setNewMode();
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);
    }

    @FXML
    void btnNavEditClick(MouseEvent event) {
        currentCustomer =  tvNavTable.getSelectionModel().getSelectedItem();
        setEditMode(currentCustomer);
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);
    }

    @FXML
    void tfNavSearchKeyTyped(KeyEvent event) {
        System.out.println("pressed");

        String strFilter = tfNavSearch.getText();

        List<Customer> filteredList = navTableArrayList.stream()
                .filter(d -> d.toString().contains(strFilter))
                .collect(Collectors.toList());

        System.out.println(filteredList);

        ObservableList<Customer> data = FXCollections.observableArrayList(filteredList);
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

        //setup up table cell factories
        colCustomerId.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        colCustFirstName.setCellValueFactory(cellData -> cellData.getValue().custFirstNameProperty());
        colCustLastName.setCellValueFactory(cellData -> cellData.getValue().custLastNameProperty());
        colCustAddress.setCellValueFactory(cellData -> cellData.getValue().custAddressProperty());
        colCustCity.setCellValueFactory(cellData -> cellData.getValue().custCityProperty());
        colCustProv.setCellValueFactory(cellData -> cellData.getValue().custProvProperty());
        colCustPostal.setCellValueFactory(cellData -> cellData.getValue().custPostalProperty());
        colCustCountry.setCellValueFactory(cellData -> cellData.getValue().custCountryProperty());
        colCustHomePhone.setCellValueFactory(cellData -> cellData.getValue().custHomePhoneProperty());
        colCustBusPhone.setCellValueFactory(cellData -> cellData.getValue().custBusPhoneProperty());
        colCustEmail.setCellValueFactory(cellData -> cellData.getValue().custEmailProperty());
        colCustUserName.setCellValueFactory(cellData -> cellData.getValue().custUserNameProperty());
        colCustPassword.setCellValueFactory(cellData -> cellData.getValue().custPasswordProperty());
//        colAgentId.setCellValueFactory(cellData -> cellData.getValue().agentIdProperty().asObject());
        colAgentId.setCellValueFactory(cellData -> cellData.getValue().agentNameProperty());

        setNavigateMode();

        //set event handlers

        //item table - double click
        tvNavTable.setOnMouseClicked( event -> {
            if( event.getClickCount() == 2 ) {
                currentCustomer = tvNavTable.getSelectionModel().getSelectedItem();
                setEditMode(currentCustomer);
            }});

        setupFocusListenerTfFname();  //First name is text
        setupFocusListenerTfLname();  //Last name is text
        setupFocusListenerTfCustEmail(); //Email is valid email
        setupFocusListenerTfCustHomePhone();  //phone is (###) ###-#### format
        setupFocusListenerTfCustBusPhone();  //phone is (###) ###-#### format
        setupFocusListenerTfCustUsername();  //Username is unique
    }

    //*******************************     NAVIGATE MODE      ********************************//

    private void setNavigateMode() {
        formMode = "Navigate";
        navTableArrayList = CustomerManager.getAllCustomers();
        ObservableList<Customer> data = FXCollections.observableArrayList(navTableArrayList);
        tvNavTable.setItems(data);

        SelectionModel<Customer> selectionModel = tvNavTable.getSelectionModel();
        selectionModel.selectFirst();
    }

    //*******************************     EDIT MODE      ********************************//
    private void setEditMode(Customer cust) {

        System.out.println("edit mode");
        formMode = "Edit";

        SelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);

        tfCustomerId.setVisible(true);
        clearDetailErrorMessages();
        loadItemDetail(cust);
    }

    //*******************************     NEW/ADD ITEM MODE      ********************************//

    private void setNewMode() {
        formMode = "New";
        tfCustomerId.setVisible(false);

        //refresh the Agency Combobox data source
        ArrayList<Agent> agents = AgentManager.getAllAgents();
        ObservableList<Agent> obsList = FXCollections.observableArrayList(agents);
        cboAgentId.setItems(obsList);

        cboAgentId.setValue(null);
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

    private void loadItemDetail(Customer c) {

        //populate controls with data
        tfCustomerId.setText(String.valueOf(c.getCustomerId()));
        tfFname.setText(c.getCustFirstName());
        tfLname.setText(c.getCustLastName());
        tfAddress.setText(c.getCustAddress());
        tfCity.setText(c.getCustCity());
        tfProv.setText(c.getCustProv());
        tfPostal.setText(c.getCustPostal());
        tfCountry.setText(c.getCustCountry());
        tfPhoneHome.setText(c.getCustHomePhone());
        tfPhoneBus.setText(c.getCustBusPhone());
        tfEmail.setText(c.getCustEmail());
        tfUsername.setText(c.getCustUserName());
        tfPassword.setText(c.getCustPassword());

        //refresh the Agency Combobox data source
        ArrayList<Agent> agents = AgentManager.getAllAgents();
        ObservableList<Agent> obsList = FXCollections.observableArrayList(agents);
        cboAgentId.setItems(obsList);

        int agentId = c.getAgentId();

        //Set agency id in combo box
        if (!Objects.equals(agentId, null) || !Objects.equals(agentId, 0)) {  //test if value is null or zero
            Agent currentAgent = AgentManager.getAgentById(c.getAgentId());
            SelectionModel<Agent> selectionModel = cboAgentId.getSelectionModel();
            selectionModel.selectFirst();
            while (selectionModel.getSelectedItem().getAgencyId() != c.getAgentId()) {
                selectionModel.selectNext();
            }
        }
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

        //test if form is in new mode, if true set ID = 0 as temp value
        if (errorCount == 0) {
            if (formMode.equals("New")) {

                Customer custChanged = new Customer(
                        0,
                        tfFname.getText(),
                        tfLname.getText(),
                        tfAddress.getText(),
                        tfCity.getText(),
                        tfProv.getText(),
                        tfPostal.getText(),
                        tfCountry.getText(),
                        tfPhoneHome.getText(),
                        tfPhoneBus.getText(),
                        tfEmail.getText(),
                        tfUsername.getText(),
                        tfPassword.getText(),
                        cboAgentId.getSelectionModel().getSelectedItem().getAgencyId());
                try {
                    //insert item into database

                    int newItemId = CustomerManager.addCustomer(custChanged);
                    System.out.println("new item #: " + newItemId);
                    if (newItemId != 0) {
                        JOptionPane.showMessageDialog(null, "New item created!",
                                "", JOptionPane.INFORMATION_MESSAGE);
                        currentCustomer = CustomerManager.getCustomerById(newItemId);
                        System.out.println(currentCustomer);
                        setEditMode(currentCustomer);

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
                Customer custChanged = new Customer(
                        Integer.parseInt(tfCustomerId.getText()),
                        tfFname.getText(),
                        tfLname.getText(),
                        tfAddress.getText(),
                        tfCity.getText(),
                        tfProv.getText(),
                        tfPostal.getText(),
                        tfCountry.getText(),
                        tfPhoneHome.getText(),
                        tfPhoneBus.getText(),
                        tfEmail.getText(),
                        tfUsername.getText(),
                        tfPassword.getText(),
                        cboAgentId.getSelectionModel().getSelectedItem().getAgencyId());
                try {
                    if (CustomerManager.updateCustomer(custChanged)) {
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


    public void setupFocusListenerTfFname() {

        tfFname.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfText(tfFname))
                    {
                        //else hide error message
                        lbErrorCustFirstName.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorCustFirstName.setVisible(true);
                        lbErrorCustFirstName.setText("First name is required.");
                        lbErrorCustFirstName.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

    public void setupFocusListenerTfLname() {

        tfLname.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfText(tfLname))
                    {
                        //else hide error message
                        lbErrorCustLastName.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorCustLastName.setVisible(true);
                        lbErrorCustLastName.setText("Last name is required.");
                        lbErrorCustLastName.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }




    public void setupFocusListenerTfCustEmail() {

        tfEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfEmail(tfEmail))
                    {
                        //else hide error message
                        lbErrorCustEmail.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorCustEmail.setVisible(true);
                        lbErrorCustEmail.setText("Provide valid email\n i.e name@domain.com");
                        lbErrorCustEmail.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

    public void setupFocusListenerTfCustHomePhone() {

        tfPhoneHome.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfPhoneNumber(tfPhoneHome))
                    {
                        //else hide error message
                        lbErrorCustHomePhone.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorCustHomePhone.setVisible(true);
                        lbErrorCustHomePhone.setText("Valid format (555) 555-5555 required");
                        lbErrorCustHomePhone.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }


    public void setupFocusListenerTfCustBusPhone() {

        tfPhoneBus.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfPhoneNumber(tfPhoneBus))
                    {
                        //else hide error message
                        lbErrorCustBusPhone.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorCustBusPhone.setVisible(true);
                        lbErrorCustBusPhone.setText("Valid format (555) 555-5555 required");
                        lbErrorCustBusPhone.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

    public void setupFocusListenerTfCustUsername() {

        tfUsername.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {

                    System.out.println(CustomerManager.checkCustomerUsername(tfUsername.getText()));
                    //run field validation rules
                    if((CustomerManager.checkCustomerUsername(tfUsername.getText()) == 0) && (Validator.isTfText(tfUsername)))
                    {
                        //else hide error message
                        lbErrorCustUsername.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorCustUsername.setVisible(true);
                        lbErrorCustUsername.setText("A unique username is required");
                        lbErrorCustUsername.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

}
