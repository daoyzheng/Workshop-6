package Controllers;

import DataAccessObjects.AgentManager;
import DomainEntities.Agent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AgentController {

    private String formMode = "Navigate";  //form mode includes: navigate, new, edit

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ColumnConstraints gpDetails;

    @FXML
    private ScrollPane spDetails;

    @FXML
    private AnchorPane btn1;

    @FXML
    private GridPane grPane;

    @FXML
    private HBox hbNavBar;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField tfAgentId;

    @FXML
    private TextField tfFname;

    @FXML
    private TextField tfMinitial;

    @FXML
    private TextField tfLname;

    @FXML
    private TextField tfBusPhone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPosition;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfAgencyId;

    @FXML
    private ComboBox<Agent> cboAgents;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;

    @FXML
    private HBox hbItemBar;

    /**************************************************************************
     *
     *                                    METHODS
     *
     **************************************************************************/

    @FXML
    void cboAgentAction(ActionEvent event) {
        //query database for agent selected
        //Agent a = AgentManager.getAgentById(cboAgents.getValue().getAgentId());
        Agent agt = cboAgents.getSelectionModel().getSelectedItem();
        loadAgent(agt);
        btnEdit.setDisable(false);
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        //disable controls
        setNavigateMode();

        //reset values in form
        if (cboAgents.getSelectionModel().getSelectedItem() != null) {
            //if agent is selected in combobox, reload agent data
            loadAgent(cboAgents.getSelectionModel().getSelectedItem());
        } else {
            //no agent selected in combobox (i.e. form was in "new" mode
            clearForm();
            btnEdit.setDisable(true);
        }
    }

    @FXML
    void btnEditClicked(MouseEvent event) {
        setEditMode();
    }

    @FXML
    void btnExitClick(MouseEvent event) {

    }

    @FXML
    void btnNewClicked(MouseEvent event) {
        setNewMode();
    }

    @FXML
    void btnSaveClicked(MouseEvent event) {
        //validate controls

        //update database, checking for concurrency errors
        try {

            boolean blUpdate = false;

            if (formMode.equals("Edit")) {
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
                        Integer.parseInt((tfAgencyId.getText()))
                );
                blUpdate = AgentManager.updateAgent(agtChanged);
                setNavigateMode();

            } else if (formMode.equals("New")) {
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
                        Integer.parseInt((tfAgencyId.getText()))
                );
                int agtId = AgentManager.addAgent(agtChanged);
                blUpdate = true;

                // get agent data from database
                ArrayList<Agent> list = AgentManager.getAllAgents();

                //create observable array and bind to combobox
                ObservableList<Agent> data = FXCollections.observableArrayList(list);
                cboAgents.setItems(data);
                tfAgentId.setVisible(true);
                setNavigateMode();

                //get and load the new agent added
                Agent a = AgentManager.getAgentById(agtId);
                loadAgent(a);
            }

            if (blUpdate == false) {
                //else send information to user that change was not successful
                System.out.println("update not successful");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void tfFnameKeyReleased(KeyEvent event) {
        System.out.println("key released");
    }


    private void setEditMode() {

        formMode = "Edit";
        hbNavBar.setDisable(true);
        hbItemBar.setVisible(true);

        //get list of all controls in the grid pane
        ObservableList<Node> nodeList = grPane.getChildren();

        //enumerate all controls and set style/properties for textfields
        for (int i = 0; i < nodeList.size(); i++) {
            //get the control
            Node n = nodeList.get(i);

            if (n instanceof TextField)  //test if node is a text field
            {
                //set style for textfield nodes
                n.setStyle("-fx-background-color: lightyellow");
                ((TextField) n).setEditable(true);
            }
        }
    }

    //set new mode
    private void setNewMode() {
        formMode = "New";
        cboAgents.setValue(null);
        hbNavBar.setDisable(true);
        hbItemBar.setVisible(true);

        //get list of all controls in the grid pane
        ObservableList<Node> nodeList = grPane.getChildren();

        //enumerate all controls and set style/properties for textfields
        for (int i = 0; i < nodeList.size(); i++) {
            //get the control
            Node n = nodeList.get(i);

            if (n instanceof TextField)  //test if node is a text field
            {
                //set style for textfield nodes
                n.setStyle("-fx-background-color: lightyellow");
                ((TextField) n).setEditable(true);
                ((TextField) n).setText("");
            }
        }
        tfAgentId.setVisible(false);
    }

    //navigate mode
    //sets the form so that all controls are read only,
    //except for the Agent selection combobox and "new" button
    private void setNavigateMode() {

        formMode = "Navigate";
        hbNavBar.setDisable(false);
        hbItemBar.setVisible(false);

        //get list of all controls in the grid pane
        ObservableList<Node> nodeList = grPane.getChildren();

        //enumerate all controls and set style/properties for textfields
        for (int i = 0; i < nodeList.size(); i++) {
            //get the control
            Node n = nodeList.get(i);

            if (n instanceof TextField)  //test if node is a text field
            {
                //set style for textfield nodes
                n.setStyle("-fx-background-color: white");
                ((TextField) n).setEditable(false);

            }
        }
    }

    private void loadAgent(Agent a) {
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


    private void clearForm() {
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
        btnEdit.setDisable(true);
    }

    @FXML
    void initialize() {
        assert btn1 != null : "fx:id=\"btn1\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfAgentId != null : "fx:id=\"tfAgentId\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfFname != null : "fx:id=\"tfFname\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfMinitial != null : "fx:id=\"tfMinitial\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfLname != null : "fx:id=\"tfLname\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfBusPhone != null : "fx:id=\"tfBusPhone\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfPosition != null : "fx:id=\"tfPosition\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfUsername != null : "fx:id=\"tfUsername\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfPassword != null : "fx:id=\"tfPassword\" was not injected: check your FXML file 'sample.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'sample.fxml'.";
        assert cboAgents != null : "fx:id=\"cboAgents\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'sample.fxml'.";


        // get agent data from database
        ArrayList<Agent> list = AgentManager.getAllAgents();

        //create observable array and bind to combobox
        ObservableList<Agent> data = FXCollections.observableArrayList(list);
        cboAgents.setItems(data);

        //clear form
        btnEdit.setDisable(true);

        //set form in read only mode
        setNavigateMode();
    }
}
