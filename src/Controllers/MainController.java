package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Styles.PrettyListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private VBox vbLeft;

    @FXML
    private Button btnExpand;

    @FXML
    private ListView<String> lvNavigation;

    private ArrayList<Pane> centerPanes = new ArrayList<>();


    /**
     * When main layout initializes, create (5) instances of center layouts (agent, customer, ...), save them in an ArrayList<Pane>, when navigation ListView get clicked, switch BoarderPane's center to corresponding layout.
     * */
    @FXML
    void initialize() {
        // setup ObservableList for navigation options
        String[] navItems = {"Agency","Agent", "Customer", "Package", "ProductSupplier", "Booking"};
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
            // todo: set center constraint to bpMain
        });
        // first time load app, select first option
        lvNavigation.getSelectionModel().select(0);
    }


    // -------- Action Methods ---------
    private boolean isExpand = true;
    public void btnExpandClicked(){
        if (isExpand){
            vbLeft.setPrefWidth(35);
            btnExpand.setText("▶");
        }
        else{
            vbLeft.setPrefWidth(140);
            btnExpand.setText("◀");
        }
        isExpand = !isExpand;
    }
}
