package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Agency;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class AgencyManager {
    //retrieve all Agencies from the database and populates Agency objects in a list
    public static ArrayList<Agency> getAllAgencies(){

        ArrayList<Agency> list = new ArrayList<>();

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();

            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT * FROM agencies");

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                list.add(new Agency(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)));
            }
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        finally {
            return list;
        }

    }

    //retrieve a customer from database based on ID
    public static Agency getAgencyById(int id) {
        Agency agncy = null;

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();
            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT * FROM agencies WHERE AgencyId=" + id);

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                agncy = new Agency(
                        Integer.parseInt(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));
            }
            System.out.println("closing conn");
            conn.close();


        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("class not found exception");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            return agncy;
        }
    }


    //retrieve a customer from database based on ID
    public static int getAgencyMaxId() {
        int maxId = 0;

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();
            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT MAX(AgencyId) FROM agencies");

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
            conn.close();

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("class not found exception");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId;
    }

    //add a new Agency to the database, returns true if successful
    public static int addAgency(Agency newAgency) throws SQLException, ClassNotFoundException {
        int newAgncyId = 0;

        String sql = "INSERT INTO agencies (" +
                "AgncyAddress, " +
                "AgncyCity, " +
                "AgncyProv, " +
                "AgncyPostal, " +
                "AgncyCountry, " +
                "AgncyPhone, " +
                "AgncyFax, " +
                "AgncyPassword, " +
                "AgencyId) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        System.out.println(sql);

        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,newAgency.getAgncyAddress());
            stmt.setString(2,newAgency.getAgncyCity());
            stmt.setString(3,newAgency.getAgncyProv());
            stmt.setString(4,newAgency.getAgncyPostal());
            stmt.setString(5,newAgency.getAgncyCountry());
            stmt.setString(6,newAgency.getAgncyPhone());
            stmt.setString(7,newAgency.getAgncyFax());
            stmt.setInt(8,newAgency.getAgencyId());


            int numRows = stmt.executeUpdate();
            if (numRows == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No rows were updated. Contact Tech Support.");
                alert.showAndWait();
            }
            else {

                //get id of new agent added to database
                //create statement
                Statement stmt2 = conn.createStatement();

                //query database
                ResultSet rs = stmt.executeQuery("SELECT MAX(AgencyId) FROM agencies");

                while (rs.next()) {
                    newAgncyId = rs.getInt(1);
                }
            }
            conn.close();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return newAgncyId;
    }


    //update an Agency record in the database, returns true if successful
    public static boolean updateAgency(Agency changedAgency) throws SQLException, ClassNotFoundException {

        String sql = "update agencies " +
                "set AgncyAddress=?, " +
                "AgncyCity=?, " +
                "AgncyProv=?, " +
                "AgncyPostal=?, " +
                "AgncyCountry=?, " +
                "AgncyPhone=?, " +
                "AgncyFax=? " +
                "where AgencyId=?";

        System.out.println(sql);
        boolean result = false;
        try {

            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,changedAgency.getAgncyAddress());
            stmt.setString(2,changedAgency.getAgncyCity());
            stmt.setString(3,changedAgency.getAgncyProv());
            stmt.setString(4,changedAgency.getAgncyPostal());
            stmt.setString(5,changedAgency.getAgncyCountry());
            stmt.setString(6,changedAgency.getAgncyPhone());
            stmt.setString(7,changedAgency.getAgncyFax());
            stmt.setInt(8,changedAgency.getAgencyId());

            int numRows = stmt.executeUpdate();
            if (numRows == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No rows were updated. Contact Tech Support.");
                alert.showAndWait();
            }
            else {
                result = true;
            }

            conn.close();

        }
        catch (SQLException e)
        {
            System.out.println("sql exception");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("class not found exception");
            e.printStackTrace();
        }
        System.out.println("finally");
        return result;
    }
}
