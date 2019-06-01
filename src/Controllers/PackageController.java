package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class PackageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnNavAdd;

    @FXML
    private Button btnNavEdit;

    @FXML
    private TextField tfNavSearch;

    @FXML
    private Button btnDetailClose;

    @FXML
    private Button btnDetailSaveClose;

    @FXML
    private Button btnDetailSave;

    @FXML
    private Button btnDetailUndo;

    @FXML
    private GridPane grPane1;

    @FXML
    private ColumnConstraints gpDetails1;

    @FXML
    private TextField tfFname1;

    @FXML
    private TextField tfBusPhone1;

    @FXML
    private TextField tfEmail1;

    @FXML
    private TextField tfPosition1;

    @FXML
    private Label lbErrorAgtFirstName1;

    @FXML
    private Label lbErrorAgtMiddleInitial1;

    @FXML
    private Label lbErrorAgtLastName1;

    @FXML
    private Label lbErrorAgtEmail1;

    @FXML
    private Label lbErrorAgtPosition1;

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
        assert btnNavAdd != null : "fx:id=\"btnNavAdd\" was not injected: check your FXML file 'package.fxml'.";
        assert btnNavEdit != null : "fx:id=\"btnNavEdit\" was not injected: check your FXML file 'package.fxml'.";
        assert tfNavSearch != null : "fx:id=\"tfNavSearch\" was not injected: check your FXML file 'package.fxml'.";
        assert btnDetailClose != null : "fx:id=\"btnDetailClose\" was not injected: check your FXML file 'package.fxml'.";
        assert btnDetailSaveClose != null : "fx:id=\"btnDetailSaveClose\" was not injected: check your FXML file 'package.fxml'.";
        assert btnDetailSave != null : "fx:id=\"btnDetailSave\" was not injected: check your FXML file 'package.fxml'.";
        assert btnDetailUndo != null : "fx:id=\"btnDetailUndo\" was not injected: check your FXML file 'package.fxml'.";
        assert grPane1 != null : "fx:id=\"grPane1\" was not injected: check your FXML file 'package.fxml'.";
        assert gpDetails1 != null : "fx:id=\"gpDetails1\" was not injected: check your FXML file 'package.fxml'.";
        assert tfFname1 != null : "fx:id=\"tfFname1\" was not injected: check your FXML file 'package.fxml'.";
        assert tfBusPhone1 != null : "fx:id=\"tfBusPhone1\" was not injected: check your FXML file 'package.fxml'.";
        assert tfEmail1 != null : "fx:id=\"tfEmail1\" was not injected: check your FXML file 'package.fxml'.";
        assert tfPosition1 != null : "fx:id=\"tfPosition1\" was not injected: check your FXML file 'package.fxml'.";
        assert lbErrorAgtFirstName1 != null : "fx:id=\"lbErrorAgtFirstName1\" was not injected: check your FXML file 'package.fxml'.";
        assert lbErrorAgtMiddleInitial1 != null : "fx:id=\"lbErrorAgtMiddleInitial1\" was not injected: check your FXML file 'package.fxml'.";
        assert lbErrorAgtLastName1 != null : "fx:id=\"lbErrorAgtLastName1\" was not injected: check your FXML file 'package.fxml'.";
        assert lbErrorAgtEmail1 != null : "fx:id=\"lbErrorAgtEmail1\" was not injected: check your FXML file 'package.fxml'.";
        assert lbErrorAgtPosition1 != null : "fx:id=\"lbErrorAgtPosition1\" was not injected: check your FXML file 'package.fxml'.";

    }
}