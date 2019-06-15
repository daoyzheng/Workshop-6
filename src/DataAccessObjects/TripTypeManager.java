package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Booking;
import DomainEntities.TripType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TripTypeManager {
    // READ
    public static ArrayList<TripType> getAllTT(){
        ArrayList<TripType> list = new ArrayList<>();
        try{
            // Get database connection
            Connection conn = DbConnection.getConnection();
            // Create query string
            String query = "SELECT * FROM triptypes";
            // Create prepare statement
            PreparedStatement stmt = conn.prepareStatement(query);
            // Execute Statement
            ResultSet rs = stmt.executeQuery();
            // Populate booking array list
            while (rs.next()){
                list.add(new TripType(
                        rs.getString(1),
                        rs.getString(2)));
            }
            conn.close();

        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return list;
    }
}
