/*
Purpose: Controller for booking
Author:  Daniel Hu
Date: May-June, 2019
 */

package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import DataAccessObjects.*;
import DataAccessObjects.TripTypeManager;
import DomainEntities.*;

import DomainEntities.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import javax.swing.*;


public class BookingController {

    // ------- My Props -------
    enum Mode{
        NAV, EDIT, ADD
    }

    String[] tripTypes = {"B", "G", "L"};

    private Mode currentMode = Mode.NAV;

    private ArrayList<Booking> bookingArrayList = new ArrayList<>();
    private Booking selectedBooking = null;

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
    private Button btnBackToList;

    @FXML
    private AnchorPane paneDetail;

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
    private ComboBox<Customer> cmbCustId;

    @FXML
    protected ComboBox<TripType> cmbTripType;

    @FXML
    private ComboBox<Package> cmbPackage;

    @FXML
    private DatePicker pickerBookDate;

    @FXML
    private Button btnGenerateBookNo;

    @FXML
    private Button btnSaveBooking;

    @FXML
    private Button btnDeleteDetail;

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
        loadBookingsIntoTable();

        // Load data into TripType & Package combobox
        loadCmbTripType();
        loadCmbPackage();

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

    public void btnAddBookingClicked(){
        enterAddMode();
    }

    public void btnBackToListClicked() {
        tabPaneBooking.getSelectionModel().select(tabOverview);
        currentMode = Mode.NAV;
    }

    public void btnGenerateBookNoClicked() {
        if (currentMode == Mode.ADD) {
            // generate a new bookNo.
            int newNo = bookingArrayList.stream()
                    .max(Comparator.comparing(Booking::getBookingId))
                    .orElseThrow(NoSuchElementException::new)
                    .getBookingId() + 1;
            tfBookNo.setText(String.valueOf(newNo));
        }
    }

    public void btnSaveBookingClicked(){
        // create Booking obj, insert
        if (currentMode != Mode.ADD) return;
        Booking newB = new Booking(0, pickerBookDate.getValue(), tfBookNo.getText(), Integer.parseInt(tfTravellerNo.getText()),
                cmbCustId.getSelectionModel().getSelectedItem().getCustomerId(),
                tripTypes[cmbTripType.getSelectionModel().getSelectedIndex()],
                cmbPackage.getSelectionModel().getSelectedItem().getPackageId());
        if (BookingManager.addBooking(newB)){
            JOptionPane.showMessageDialog(null, "New booking is reserved!", "Congrats!", JOptionPane.INFORMATION_MESSAGE);
            // refresh table
            loadBookingsIntoTable();
        }
        else
            JOptionPane.showMessageDialog(null, "Something went wrong, please try again.", "Ops", JOptionPane.ERROR_MESSAGE);
        btnBackToListClicked();
    }

    public void btnDeleteDetailClicked(){
        // delete current selected booking
        selectedBooking = tvOverviewBooking.getSelectionModel().getSelectedItem();
        if (selectedBooking != null){
            if (BookingManager.deleteBooking(selectedBooking) > 0){
                JOptionPane.showMessageDialog(null, "Selected booking is deleted!", "Done!", JOptionPane.INFORMATION_MESSAGE);
                loadBookingsIntoTable();
            } else
                JOptionPane.showMessageDialog(null, "Something went wrong, please try again.", "Ops", JOptionPane.ERROR_MESSAGE);
            btnBackToListClicked();
        }

    }

    public void cmbCustIdSelected(){
        // output customer name to tf
        Customer selectedCust = cmbCustId.getSelectionModel().getSelectedItem();
        tfFirstName.setText(selectedCust.getCustFirstName());
        tfLastName.setText(selectedCust.getCustLastName());
    }


    /****************************************************************************
     *
     *                              Convenient Methods
     *
     *
     * **************************************************************************/

    private void loadBookingsIntoTable() {
        bookingArrayList = BookingManager.getAllBookings();
        ObservableList<Booking> bookings = FXCollections.observableArrayList(bookingArrayList);
        tvOverviewBooking.setItems(bookings);
    }


    private void loadCmbTripType() {
        ArrayList<TripType> list = TripTypeManager.getAllTT();
        ObservableList<TripType> TTs = FXCollections.observableArrayList(list);
        cmbTripType.setItems(TTs);
    }

    private void loadCmbPackage() {
        cmbPackage.setItems(PackageManager.getAllPackages());
        cmbPackage.setConverter(new StringConverter<Package>() {
            @Override
            public String toString(Package object) {
                return object.getPkgName() + ": $" + object.getPkgBasePrice();
            }

            @Override
            public Package fromString(String string) {
                return null;
            }
        });
    }

    private void enterAddMode(){
        currentMode = Mode.ADD;
        tabPaneBooking.getSelectionModel().select(tabDetail);
        clearDetailForm(grPane);
        cmbCustId.setVisible(true);
        ArrayList<Customer> customers = CustomerManager.getAllCustomers();
        cmbCustId.setItems(FXCollections.observableArrayList(customers));
    }

    private void clearDetailForm(Pane pane) {
        //get list of all controls in the grid pane
        ObservableList<Node> nodeList = pane.getChildren();

        //enumerate all controls and reset textfields
        for (int i = 0; i < nodeList.size(); i++) {
            Node n = nodeList.get(i);
            //test if node is text field / picker / combobox, unset them
            if (n instanceof TextField)
                ((TextField) n).setText("");
            if (n instanceof DatePicker)
                ((DatePicker) n).setValue(null);
            if (n instanceof ComboBox)
                ((ComboBox) n).getSelectionModel().select(-1);
        }
        tvBookingDetail.setItems(null);
        tfFirstName.clear();
        tfBookNo.clear();
    }

    private void enterEditMode(Booking selectedB) {
        currentMode = Mode.EDIT;
        tabPaneBooking.getSelectionModel().select(tabDetail);
        cmbCustId.setVisible(false);

        // display details
        tfBookId.setText(String.valueOf(selectedB.getBookingId()));
        pickerBookDate.setValue(selectedB.getBookingDate());
        tfBookNo.setText(selectedB.getBookingNo());
        tfTravellerNo.setText(String.valueOf(selectedB.getTravelerCount()));
        Customer customer = CustomerManager.getCustomerById(selectedB.getCustomerId());
        tfFirstName.setText(customer.getCustFirstName());
        tfLastName.setText(customer.getCustLastName());
        cmbTripType.getSelectionModel().select(Arrays.asList(tripTypes).indexOf(selectedB.getTripTypeId()));
        Package bookedPackge = PackageManager.getPackageById(selectedB.getPackageId());
//        tfPackage.setText(bookedPackge==null ? "N/A" : bookedPackge.getPkgName());
        if (bookedPackge != null)
            cmbPackage.getSelectionModel().select(bookedPackge);

        // load booking detail table
        ArrayList<BookingDetail> details = BookingDetailManager.getBookingDetailsByBookingId(selectedB.getBookingId());
        tvBookingDetail.setItems(FXCollections.observableArrayList(details));
    }



}
