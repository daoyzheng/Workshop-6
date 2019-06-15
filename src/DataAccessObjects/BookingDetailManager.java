/*
Purpose: Data Access Class for BookingDetails
Author:  DongMing Hu
Date: June, 2019
 */


package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Booking;
import DomainEntities.BookingDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingDetailManager {

    // READ
    /***
     * @param  bookingId to use to find according details
     * @return
     *
     * */
    public static ArrayList<BookingDetail> getBookingDetailsByBookingId(int bookingId){

        ArrayList<BookingDetail> details = new ArrayList<>();

        try {
            // Get Db connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM bookingdetails WHERE BookingId=?";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Assign parameter for statement
            stmt.setInt(1,bookingId);
            // Execute statement
            ResultSet rs = stmt.executeQuery();
            // Get first row of data
            while (rs.next()) {
                details.add(new BookingDetail(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDate(3).toLocalDate(),
                        rs.getDate(4).toLocalDate(),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13)
                        ));
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return details;
    }


}
