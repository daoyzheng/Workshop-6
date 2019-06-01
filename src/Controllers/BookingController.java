package Controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import DomainEntities.Booking;
import DomainEntities.BookingDetail;
import DomainEntities.TripType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class BookingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab tabOverview;

    @FXML
    private TextField tfSearchBarBooking;

    @FXML
    private TableView<Booking> tvOverviewBooking;

    @FXML
    private TableColumn<Booking, Integer> colBookId;

    @FXML
    private TableColumn<Booking, Date> colBookDate;

    @FXML
    private TableColumn<Booking, String> colBookNo;

    @FXML
    private TableColumn<Booking, Integer> colTravellerNo;

    @FXML
    private TableColumn<Booking, String> colCustName;

    @FXML
    private TableColumn<Booking, String> colTripType;

    @FXML
    private TableColumn<Booking, Integer> colPackage;

    @FXML
    private Button btnAddBooking;

    @FXML
    private Button btnDetailBooking;

    @FXML
    private Button btnDeleteBooking;

    // --------- Tab Detail Controls Starts Here -----------

    @FXML
    private Tab tabDetail;

    @FXML
    private GridPane grPane;

    @FXML
    private ColumnConstraints gpDetails;  // what's this?

    @FXML
    private TextField tfBookNo;

    @FXML
    private TextField tfTravellerNo;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfPackage;

    @FXML
    private TextField tfBookId;

    @FXML
    private Label lblBookNoError;

    @FXML
    private Label lblTravellerNoError;

    @FXML
    private Label lblFirstNameError;

    @FXML
    private Label lblLastNameError;

    @FXML
    private Label lblTripTypeError;

    @FXML
    private Label lblPackageError;

    @FXML
    private Label lblBookDateError;

    @FXML
    private Label lblBookIdError;

    @FXML
    private ComboBox<TripType> cmbTripType;

    @FXML
    private DatePicker pickerBookDate;

    @FXML
    private Button btnSaveDetail;

    @FXML
    private Button btnResetDetail;

    @FXML
    private TableView<BookingDetail> tvBookingDetail;

    @FXML
    private TableColumn<BookingDetail, Integer> colDetailId;

    @FXML
    private TableColumn<BookingDetail, Integer> colItineraryNo;

    @FXML
    private TableColumn<BookingDetail, Date> colStartDate;

    @FXML
    private TableColumn<BookingDetail, Date> colEndDate;

    @FXML
    private TableColumn<BookingDetail, String> colDesc;

    @FXML
    private TableColumn<BookingDetail, String> colDestination;

    @FXML
    private TableColumn<BookingDetail, Double> colBasePrice;

    @FXML
    private TableColumn<BookingDetail, Double> colCommission;

    @FXML
    private TableColumn<BookingDetail, Integer> colProductSupplier;

    @FXML
    void initialize() {



    }
}
