package Controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import DataAccessObjects.AgentManager;
import DomainEntities.Agent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;

public class AgentController {


    //form level variables
    private String formMode;  //variable to track if form is in add/edit/navigate mode
    private  ArrayList<Agent> navTableArrayList;  //array list used to populate the navigation pane table
    private Agent currentAgent;  //the current agent selected and shown in the detail view

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private TabPane tabpaneMain;

    //Table/List tab controls
    @FXML private Tab tabNav;
    @FXML private TableView<Agent> tvNavTable;
    @FXML private Button btnNavAdd;
    @FXML private Button btnNavEdit;
    @FXML private Button btnNavAgencies;
    @FXML private TextField tfNavSearch;

    @FXML private TableColumn<Agent, Integer> colAgentId;
    @FXML private TableColumn<Agent, String> colAgtFirstName;
    @FXML private TableColumn<Agent, String> colAgtMiddleInitial;
    @FXML private TableColumn<Agent, String> colAgtLastName;
    @FXML private TableColumn<Agent, String> colAgtBusPhone;
    @FXML private TableColumn<Agent, String> colAgtEmail;
    @FXML private TableColumn<Agent, String> colAgtPosition;
    @FXML private TableColumn<Agent, String> colAgtUsername;
    @FXML private TableColumn<Agent, String> colAgtPassword;
    @FXML private TableColumn<Agent, Integer> colAgencyId;


    //Detail tab controls
    @FXML private Tab tabDetail;
    @FXML private Button btnDetailClose;
    @FXML private Button btnDetailSaveClose;
    @FXML private Button btnDetailSave;
    @FXML private Button btnDetailUndo;
    @FXML private GridPane grPane;
    @FXML private ColumnConstraints gpDetails;

    //domain specific controls
    @FXML private TextField tfAgentId;
    @FXML private TextField tfFname;
    @FXML private TextField tfMinitial;
    @FXML private TextField tfLname;
    @FXML private TextField tfBusPhone;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPosition;
    @FXML private TextField tfUsername;
    @FXML private TextField tfPassword;
    @FXML private TextField tfAgencyId;
    @FXML private Label lbErrorAgtFirstName;
    @FXML private Label lbErrorAgtMiddleInitial;
    @FXML private Label lbErrorAgtLastName;
    @FXML private Label lbErrorAgtBusPhone;
    @FXML private Label lbErrorAgtEmail;
    @FXML private Label lbErrorAgtPosition;
    @FXML private Label lbErrorAgtUsername;
    @FXML private Label lbErrorAgtPassword;
    @FXML private Label lbErrorAgtAgencyID;

    /****************************************************************************
     *
     *                              BUTTON EVENT HANDLERS
     *
     ****************************************************************************/

    @FXML
    void btnDetailCloseClicked(MouseEvent event) {

        //clear detail form inputs and set currentAgent object to null
        clearDetailForm();
        currentAgent = null;

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
         loadItemDetail(currentAgent);
    }

    @FXML
    void btnNavAddClick(MouseEvent event) {
        setNewMode();
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);
    }

    @FXML
    void btnNavAgenciesClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/agency.fxml"));
        Parent agency = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage agencies");
        stage.setScene(new Scene(agency));
        stage.show();
    }

    @FXML
    void btnNavEditClick(MouseEvent event) {
        currentAgent =  tvNavTable.getSelectionModel().getSelectedItem();
        setEditMode(currentAgent);
        SingleSelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);
    }

    @FXML
    void tfNavSearchKeyTyped(KeyEvent event) {
           System.out.println("pressed");

        String strFilter = tfNavSearch.getText();

        List<Agent> filteredList = navTableArrayList.stream()
                .filter(d -> d.toString().contains(strFilter))
                .collect(Collectors.toList());

        System.out.println(filteredList);

        ObservableList<Agent> data = FXCollections.observableArrayList(filteredList);
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

        assert tabpaneMain != null : "fx:id=\"tabpaneMain\" was not injected: check your FXML file 'agent.fxml'.";
        assert tabNav != null : "fx:id=\"tabNav\" was not injected: check your FXML file 'agent.fxml'.";
        assert tvNavTable != null : "fx:id=\"tvNavTable\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtFirstName != null : "fx:id=\"colAgtFirstName\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtMiddleInitial != null : "fx:id=\"colAgtMiddleInitial\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtLastName != null : "fx:id=\"colAgtLastName\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtBusPhone != null : "fx:id=\"colAgtBusPhone\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtEmail != null : "fx:id=\"colAgtEmail\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtPosition != null : "fx:id=\"colAgtPosition\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtUsername != null : "fx:id=\"colAgtUsername\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgtPassword != null : "fx:id=\"colAgtPassword\" was not injected: check your FXML file 'agent.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'agent.fxml'.";
        assert tabDetail != null : "fx:id=\"tabDetail\" was not injected: check your FXML file 'agent.fxml'.";
        assert grPane != null : "fx:id=\"grPane\" was not injected: check your FXML file 'agent.fxml'.";
        assert gpDetails != null : "fx:id=\"gpDetails\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfAgentId != null : "fx:id=\"tfAgentId\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfFname != null : "fx:id=\"tfFname\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfMinitial != null : "fx:id=\"tfMinitial\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfLname != null : "fx:id=\"tfLname\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfBusPhone != null : "fx:id=\"tfBusPhone\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfPosition != null : "fx:id=\"tfPosition\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfPassword != null : "fx:id=\"tfPassword\" was not injected: check your FXML file 'agent.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtFirstName != null : "fx:id=\"lbErrorAgtFirstName\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtMiddleInitial != null : "fx:id=\"lbErrorAgtMiddleInitial\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtLastName != null : "fx:id=\"lbErrorAgtLastName\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtBusPhone != null : "fx:id=\"lbErrorAgtBusPhone\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtEmail != null : "fx:id=\"lbErrorAgtEmail\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtPosition != null : "fx:id=\"lbErrorAgtPosition\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtUsername != null : "fx:id=\"lbErrorAgtUsername\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtPassword != null : "fx:id=\"lbErrorAgtPassword\" was not injected: check your FXML file 'agent.fxml'.";
        assert lbErrorAgtAgencyID != null : "fx:id=\"lbErrorAgtAgencyID\" was not injected: check your FXML file 'agent.fxml'.";


        //setup up table cell factories
        colAgentId.setCellValueFactory(cellData -> cellData.getValue().agentIdProperty().asObject());
        colAgtFirstName.setCellValueFactory(cellData -> cellData.getValue().agtFirstNameProperty());
        colAgtMiddleInitial.setCellValueFactory(cellData -> cellData.getValue().agtMiddleInitialProperty());
        colAgtLastName.setCellValueFactory(cellData -> cellData.getValue().agtLastNameProperty());
        colAgtBusPhone.setCellValueFactory(cellData -> cellData.getValue().agtBusPhoneProperty());
        colAgtEmail.setCellValueFactory(cellData -> cellData.getValue().agtEmailProperty());
        colAgtPosition.setCellValueFactory(cellData -> cellData.getValue().agtEmailProperty());
        colAgtUsername.setCellValueFactory(cellData -> cellData.getValue().agtUserNameProperty());
        colAgtPassword.setCellValueFactory(cellData -> cellData.getValue().agtPositionProperty());
        colAgencyId.setCellValueFactory(cellData -> cellData.getValue().agencyIdProperty().asObject());

        setNavigateMode();

        //set event handlers

        //item table - double click
        tvNavTable.setOnMouseClicked( event -> {
            if( event.getClickCount() == 2 ) {
                currentAgent = tvNavTable.getSelectionModel().getSelectedItem();
                setEditMode(currentAgent);
            }});

        setupFocusListenerTfFname();  //First name is text
        setupFocusListenerTfLname();  //Last name is text
        setupFocusListenerTfAgtEmail(); //Email is valid email
        setupFocusListenerTfAgtBusPhone();  //phone is (###) ###-#### format
        setupFocusListenerTfAgtUsername();  //Username is unique
     }

    //*******************************     NAVIGATE MODE      ********************************//

    private void setNavigateMode() {
        formMode = "Navigate";
        navTableArrayList = AgentManager.getAllAgents();
        ObservableList<Agent> data = FXCollections.observableArrayList(navTableArrayList);
        tvNavTable.setItems(data);
    }

    //*******************************     EDIT MODE      ********************************//
    private void setEditMode(Agent agt) {
        formMode = "Edit";
        SelectionModel<Tab> selectionModel = tabpaneMain.getSelectionModel();
        selectionModel.select(tabDetail);

        tfAgentId.setVisible(true);
        clearDetailErrorMessages();
        loadItemDetail(agt);
    }

    //*******************************     NEW/ADD ITEM MODE      ********************************//

    private void setNewMode() {
        formMode = "New";
        System.out.println(formMode);
        tfAgentId.setVisible(false);
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

    private void loadItemDetail(Agent a) {

        //populate controls with data
        tfAgentId.setText(String.valueOf(a.getAgentId()));
        tfFname.setText(a.getAgtFirstName());
        tfMinitial.setText(a.getAgtMiddleInitial());
        tfLname.setText(a.getAgtLastName());
        tfBusPhone.setText(a.getAgtBusPhone());
        tfEmail.setText(a.getAgtEmail());
        tfPosition.setText(a.getAgtPosition());
        tfUsername.setText(a.getAgtUserName());
        tfPassword.setText(a.getAgtPassword());
        tfAgencyId.setText(String.valueOf(a.getAgencyId()));
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

                Agent agtChanged = new Agent(
                        0,
                        tfFname.getText(),
                        tfMinitial.getText(),
                        tfLname.getText(),
                        tfBusPhone.getText(),
                        tfEmail.getText(),
                        tfPosition.getText(),
                        tfUsername.getText(),
                        tfPassword.getText(),
                        Integer.parseInt(tfAgencyId.getText()));
                try {
                    //insert item into database

                    int newItemId = AgentManager.addAgent(agtChanged);

                    if (newItemId != 0) {
                        JOptionPane.showMessageDialog(null, "New item created!",
                                "", JOptionPane.INFORMATION_MESSAGE);
                        currentAgent = AgentManager.getAgentById(newItemId);
                        setEditMode(currentAgent);
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
                Agent agtChanged = new Agent(
                        Integer.parseInt(tfAgentId.getText()),
                        tfFname.getText(),
                        tfMinitial.getText(),
                        tfLname.getText(),
                        tfBusPhone.getText(),
                        tfEmail.getText(),
                        tfPosition.getText(),
                        tfUsername.getText(),
                        tfPassword.getText(),
                        Integer.parseInt(tfAgencyId.getText()));



                try {
                    if (AgentManager.updateAgent(agtChanged)) {
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


    /*******************************     FILTER NAV TABLE      ********************************/

    private void filterNavTable() {






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
                        lbErrorAgtFirstName.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgtFirstName.setVisible(true);
                        lbErrorAgtFirstName.setText("First name is required.");
                        lbErrorAgtFirstName.setStyle("-fx-text-fill:  red");
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
                        lbErrorAgtLastName.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgtLastName.setVisible(true);
                        lbErrorAgtLastName.setText("Last name is required.");
                        lbErrorAgtLastName.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }




    public void setupFocusListenerTfAgtEmail() {

        tfEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfEmail(tfEmail))
                    {
                        //else hide error message
                        lbErrorAgtEmail.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgtEmail.setVisible(true);
                        lbErrorAgtEmail.setText("Provide valid email\n i.e name@domain.com");
                        lbErrorAgtEmail.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

    public void setupFocusListenerTfAgtBusPhone() {

        tfBusPhone.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {
                    //run field validation rules
                    if(Validator.isTfPhoneNumber(tfBusPhone))
                    {
                        //else hide error message
                        lbErrorAgtBusPhone.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgtBusPhone.setVisible(true);
                        lbErrorAgtBusPhone.setText("Valid format (555) 555-5555 required");
                        lbErrorAgtBusPhone.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

    public void setupFocusListenerTfAgtUsername() {

        tfUsername.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue)  //when field loses focus
                {

                    System.out.println(AgentManager.checkAgentUsername(tfUsername.getText()));
                    //run field validation rules
                    if((AgentManager.checkAgentUsername(tfUsername.getText()) == 0) && (Validator.isTfText(tfUsername)))
                    {
                        //else hide error message
                        lbErrorAgtUsername.setVisible(false);
                    }
                    else
                    {
                        //if not valid, display error msg
                        lbErrorAgtUsername.setVisible(true);
                        lbErrorAgtUsername.setText("A unique username is required");
                        lbErrorAgtUsername.setStyle("-fx-text-fill:  red");
                    }
                }
            }
        });
    }

//    public void setupFocusListerTfSearch() {
//
//        .focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
//                if (!newPropertyValue)  //when field loses focus
//                {
//
//                    System.out.println(AgentManager.checkAgentUsername(tfUsername.getText()));
//                    //run field validation rules
//                    if((AgentManager.checkAgentUsername(tfUsername.getText()) == 0) && (Validator.isTfText(tfUsername)))
//                    {
//                        //else hide error message
//                        lbErrorAgtUsername.setVisible(false);
//                    }
//                    else
//                    {
//                        //if not valid, display error msg
//                        lbErrorAgtUsername.setVisible(true);
//                        lbErrorAgtUsername.setText("A unique username is required");
//                        lbErrorAgtUsername.setStyle("-fx-text-fill:  red");
//                    }
//                }
//            }
//        });
//    }


}

