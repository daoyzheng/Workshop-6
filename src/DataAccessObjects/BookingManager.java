/*
Purpose: Data Access Class for Bookings
Author:  DongMing Hu
Date: May, 2019
 */

package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookingManager {
    // READ
    public static ArrayList<Booking> getAllBookings(){
        ArrayList<Booking> list = new ArrayList<>();
        try {
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM bookings";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Execute Statement
            ResultSet rs = stmt.executeQuery();
            // Populate booking array list
            while (rs.next()){
                list.add(new Booking(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7)));  // if Null, return 0
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param id bookingId
     * @return return null if cannot find corresponding result
     */
    public static Booking getBookingById(int id){

        Booking booking = null;

        try {
            // Get Db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM bookings WHERE BookingId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Assign parameter for prepare statement
            stmt.setInt(1,id);
            // Execute statement
            ResultSet rs = stmt.executeQuery();
            // Get first row of data
            if (rs.next()) {
                booking = new Booking(
                        rs.getInt(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7));
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return booking;
    }

    // CREATE
    /**
     * @param newB  a booking object
     * @return a bool indicate if record was successfully inserted
     */
    public static boolean addBooking(Booking newB){
        boolean isSuccess = false;
        try {
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "INSERT INTO bookings " +
                    "VALUES(?,?,?,?,?,?)";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // Assign parameters
            stmt.setDate(1, Date.valueOf(newB.getBookingDate()));
            stmt.setString(2, newB.getBookingNo());
            stmt.setInt(3, newB.getTravelerCount());
            stmt.setInt(4, newB.getCustomerId());
            stmt.setString(5, newB.getTripTypeId());
            stmt.setInt(6, newB.getPackageId());
            // Execute query
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to add to bookings table");
            } else {
                isSuccess = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    // UPDATE

    /**
     *
     * @param newB new Booking object
     * @param oldB old Booking object with same id
     * @return a bool indicate if update was successful
     */
    public static boolean updateBooking(Booking newB, Booking oldB){
        boolean isSuccess = false;
        try {
            if (newB.getBookingId() != oldB.getBookingId())
                throw new Exception("New record's id doesn't match the old id, abort update.");
            // Get db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "UPDATE bookings SET BookingDate = ?, BookingNo = ?, TravelerCount = ?, CustomerId = ?, TripTypeId = ?, PackageId = ? WHERE bookings.BookingId = ?";
            // todo: should compare all columns to avoid concurrency issue, but I'm too lazy to do that
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Assign parameter
            stmt.setDate(1, Date.valueOf(newB.getBookingDate()));
            stmt.setString(2,newB.getBookingNo());
            stmt.setInt(3, newB.getTravelerCount());
            stmt.setInt(4, newB.getCustomerId());
            stmt.setString(5, newB.getTripTypeId());
            stmt.setInt(6, newB.getPackageId());
            stmt.setInt(7, oldB.getBookingId());
            // Execute query
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                throw new Exception("Failed to update to bookings table");
            }else {
                isSuccess = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSuccess;
    }
}
