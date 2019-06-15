package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import DataAccessObjects.*;
import DomainEntities.Booking;
import DomainEntities.BookingDetail;
import DomainEntities.Customer;
import DomainEntities.Package;
import DomainEntities.TripType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class BookingController {

    // ------- My Props -------
    enum Mode{
        NAV, EDIT, ADD;
    }

    private Mode currentMode = Mode.NAV;

    private ArrayList<Booking> bookingArrayList;
    private Booking selectedBooking;

    // ------- FXML Controls Starts Here -------

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab tabOverview;

    @FXML
    private TextField tfSearchBarBooking;

    @FXML
    private TabPane tabPaneBooking;

    @FXML
    private TableView<Booking> tvOverviewBooking;

    @FXML
    private TableColumn<Booking, Integer> colBookId;

    @FXML
    private TableColumn<Booking, LocalDate> colBookDate;

    @FXML
    private TableColumn<Booking, String> colBookNo;

    @FXML
    private TableColumn<Booking, Integer> colTravellerNo;

    @FXML
    private TableColumn<Booking, Integer> colCustName;

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
    private TableColumn<BookingDetail, LocalDate> colStartDate;

    @FXML
    private TableColumn<BookingDetail, LocalDate> colEndDate;

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
        // Set up booking table columns
        colBookId.setCellValueFactory(param -> param.getValue().bookingIdProperty().asObject());
        colBookDate.setCellValueFactory(param -> param.getValue().bookingDateProperty());
        colBookNo.setCellValueFactory(param -> param.getValue().bookingNoProperty());
        colTravellerNo.setCellValueFactory(param -> param.getValue().travelerCountProperty().asObject());
        colCustName.setCellValueFactory(param -> param.getValue().customerIdProperty().asObject());
        colTripType.setCellValueFactory(param -> param.getValue().tripTypeIdProperty());
        colPackage.setCellValueFactory(param -> param.getValue().packageIdProperty().asObject());

        // Set up booking detail table columns
        colDetailId.setCellValueFactory(param -> param.getValue().bookingDetailIdProperty().asObject());
        colItineraryNo.setCellValueFactory(param -> param.getValue().itineraryNoProperty().asObject());
        colStartDate.setCellValueFactory(param -> param.getValue().tripStartProperty());
        colEndDate.setCellValueFactory(param -> param.getValue().tripEndProperty());
        colDesc.setCellValueFactory(param -> param.getValue().descriptionProperty());
        colDestination.setCellValueFactory(param -> param.getValue().destinationProperty());
        colBasePrice.setCellValueFactory(param -> param.getValue().basePriceProperty().asObject());
        colCommission.setCellValueFactory(param -> param.getValue().agencyCommissionProperty().asObject());
        colProductSupplier.setCellValueFactory(param -> param.getValue().productSupplierIdProperty().asObject());



        // Load data into table
        bookingArrayList = BookingManager.getAllBookings();
        ObservableList<Booking> bookings = FXCollections.observableArrayList(bookingArrayList);
        tvOverviewBooking.setItems(bookings);
    }

    /****************************************************************************
     *
     *                              Handler Methods
     *
     *
     * **************************************************************************/

    public void btnDetailBookingClicked(){
        selectedBooking = tvOverviewBooking.getSelectionModel().getSelectedItem();
        if (selectedBooking != null){
            enterEditMode(selectedBooking);
        }
    }


    /****************************************************************************
     *
     *                              Convenient Methods
     *
     *
     * **************************************************************************/

    private void enterEditMode(Booking selectedB) {
        currentMode = Mode.EDIT;
        tabPaneBooking.getSelectionModel().select(tabDetail);

        // display details
        tfBookId.setText(String.valueOf(selectedB.getBookingId()));
        pickerBookDate.setValue(selectedB.getBookingDate());
        tfBookNo.setText(selectedB.getBookingNo());
        tfTravellerNo.setText(String.valueOf(selectedB.getTravelerCount()));
        Customer customer = CustomerManager.getCustomerById(selectedB.getCustomerId());
        tfFirstName.setText(customer.getCustFirstName());
        tfLastName.setText(customer.getCustLastName());
        ArrayList<TripType> list = TripTypeManager.getAllTT();
        ObservableList<TripType> TTs = FXCollections.observableArrayList(list);
        cmbTripType.setItems(TTs);
        String[] tripTypes = {"B", "G", "L"};
        cmbTripType.getSelectionModel().select(Arrays.asList(tripTypes).indexOf(selectedB.getTripTypeId()));
        Package bookedPackge = PackageManager.getPackageById(selectedB.getPackageId());
        tfPackage.setText(bookedPackge==null ? "N/A" : bookedPackge.getPkgName());

        // load booking detail table
        ArrayList<BookingDetail> details = BookingDetailManager.getBookingDetailsByBookingId(selectedB.getBookingId());
        tvBookingDetail.setItems(FXCollections.observableArrayList(details));
    }

}
