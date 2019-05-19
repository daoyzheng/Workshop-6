package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane bpMain;

    @FXML
    private MenuBar menuBarTop;

    @FXML
    private ListView<String> lvNavigation;

    ArrayList<Pane> centerPanes = new ArrayList<>();


    @FXML
    void initialize() {
        // setup ObservableList for navigation options
        String[] navItems = {"Agent", "Customer", "Package", "Product", "Booking"};
        ObservableList<String> navs = FXCollections.observableList(Arrays.asList(navItems));
        // setup ArrayList<Pane> centerPanes with loaded panes
        for (String nav: navs) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("../Views/" + nav.toLowerCase() + ".fxml"));
            try {
                Pane centerPane = loader.load();
                centerPanes.add(centerPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // load navs into ListView
        lvNavigation.setItems(navs);
        // add listener to ListView: when selected change, switch center pane
        lvNavigation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // switch center according to selected value
            Pane centerPane = centerPanes.get(navs.indexOf(newValue));
            bpMain.setCenter(centerPane);
        });
        // first time load app, select first option
        lvNavigation.getSelectionModel().select(0);
    }
}
