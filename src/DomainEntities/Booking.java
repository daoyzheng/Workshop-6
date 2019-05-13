/*
Purpose: Domain entity for bookings
Author:  DongMing Hu
Date: May, 2019
 */

package DomainEntities;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
//import java.util.Date;
import java.sql.Date;

public class Booking {
    // PK
    private SimpleIntegerProperty bookingId;

    private ObjectProperty<Date> bookingDate;  // ? Date or LocalDate
    private SimpleStringProperty bookingNo;
    private SimpleIntegerProperty travelerCount;
    // FKs
    private SimpleIntegerProperty customerId;
    private SimpleIntegerProperty tripTypeId;
    private SimpleIntegerProperty packageId;

    // Constructor

    public Booking(int bookingId, Date bookingDate, String bookingNo, int travelerCount, int customerId, int tripTypeId, int packageId) {
        this.bookingId.set(bookingId);
        this.bookingDate.set(bookingDate);
        this.bookingNo.set(bookingNo);
        this.travelerCount.set(travelerCount);
        this.customerId.set(customerId);
        this.tripTypeId.set(tripTypeId);
        this.packageId.set(packageId);
    }

    // getters & setters

    public int getBookingId() {
        return bookingId.get();
    }

    public SimpleIntegerProperty bookingIdProperty() {
        return bookingId;
    }
    /* shouldn't set PK
    public void setBookingId(int bookingId) {
        this.bookingId.set(bookingId);
    }*/

    public Date getBookingDate() {
        return bookingDate.get();
    }

    public ObjectProperty<Date> bookingDateProperty() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public String getBookingNo() {
        return bookingNo.get();
    }

    public SimpleStringProperty bookingNoProperty() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo.set(bookingNo);
    }

    public int getTravelerCount() {
        return travelerCount.get();
    }

    public SimpleIntegerProperty travelerCountProperty() {
        return travelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        this.travelerCount.set(travelerCount);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public int getTripTypeId() {
        return tripTypeId.get();
    }

    public SimpleIntegerProperty tripTypeIdProperty() {
        return tripTypeId;
    }

    public void setTripTypeId(int tripTypeId) {
        this.tripTypeId.set(tripTypeId);
    }

    public int getPackageId() {
        return packageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }
}
