package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Agent;
import DomainEntities.Customer;
import javafx.scene.control.Alert;

import javax.lang.model.element.NestingKind;
import java.sql.*;
import java.util.ArrayList;

public class CustomerManager {

    //retrieve all Customers from the database and populates Customer objects in a list
    public static ArrayList<Customer> getAllCustomers(){

        ArrayList<Customer> list = new ArrayList<>();

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();

            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getInt(14)));
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
    public static Customer getCustomerById(int id) {
        Customer cust = null;

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();
            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers WHERE CustomerId=" + id);

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                cust = new Customer(
                        Integer.parseInt(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        Integer.parseInt(rs.getString(14)));
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

        finally {
            return cust;
        }
    }


    //add a new Customer to the database, returns true if successful
    public static int addCustomer(Customer newCustomer) throws SQLException, ClassNotFoundException {
        int newCustId = 0;

        String sql = "INSERT INTO customers (" +
                "CustFirstName, " +
                "CustLastName, " +
                "CustAddress, " +
                "CustCity, " +
                "CustProv, " +
                "CustPostal, " +
                "CustCountry, " +
                "CustHomePhone, " +
                "CustBusPhone, " +
                "CustEmail, " +
                "CustUserName, " +
                "CustPassword, " +
                "AgentId" +
                ") " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,newCustomer.getCustFirstName());
            stmt.setString(2,newCustomer.getCustLastName());
            stmt.setString(3,newCustomer.getCustAddress());
            stmt.setString(4,newCustomer.getCustCity());
            stmt.setString(5,newCustomer.getCustProv());
            stmt.setString(6,newCustomer.getCustPostal());
            stmt.setString(7,newCustomer.getCustCountry());
            stmt.setString(8,newCustomer.getCustHomePhone());
            stmt.setString(9,newCustomer.getCustBusPhone());
            stmt.setString(10,newCustomer.getCustEmail());
            stmt.setString(11,newCustomer.getCustUserName());
            stmt.setString(12,newCustomer.getCustPassword());
            stmt.setInt(13,newCustomer.getAgentId());


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
                ResultSet rs = stmt.executeQuery("SELECT MAX(CustomerId) FROM customers");

                while (rs.next()) {
                    newCustId = rs.getInt(1);
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
        return newCustId;
    }


    //update an Customer record in the database, returns true if successful
    public static boolean updateCustomer(Customer changedCustomer) throws SQLException, ClassNotFoundException {

        String sql = "update customers " +
                "set CustFirstName=?, " +
                "CustLastName=?, " +
                "CustAddress=?, " +
                "CustCity=?, " +
                "CustProv=?, " +
                "CustPostal=?, " +
                "CustCountry=?, " +
                "CustHomePhone=?, " +
                "CustBusPhone=?, " +
                "CustEmail=?, " +
                "CustUserName=?, " +
                "CustPassword=?, " +
                "AgentId=? " +
                "where CustomerId=?";

        boolean result = false;
        try {

            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,changedCustomer.getCustFirstName());
            stmt.setString(2,changedCustomer.getCustLastName());
            stmt.setString(3,changedCustomer.getCustAddress());
            stmt.setString(4,changedCustomer.getCustCity());
            stmt.setString(5,changedCustomer.getCustProv());
            stmt.setString(6,changedCustomer.getCustPostal());
            stmt.setString(7,changedCustomer.getCustCountry());
            stmt.setString(8,changedCustomer.getCustHomePhone());
            stmt.setString(9,changedCustomer.getCustBusPhone());
            stmt.setString(10,changedCustomer.getCustEmail());
            stmt.setString(11,changedCustomer.getCustUserName());
            stmt.setString(12,changedCustomer.getCustPassword());
            stmt.setInt(13,changedCustomer.getAgentId());
            stmt.setInt(14,changedCustomer.getCustomerId());

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
        return result;
    }

    //check if a username is unique value
    public static int checkCustomerUsername(String username) {
        int result = 0;

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();
            //create statement
            Statement stmt = conn.createStatement();
            String sql = "SELECT COUNT(CustUserName) As rowcount FROM customers WHERE CustUserName='" + username + "'";
            //query database
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.last()){
                result = rs.getInt(1);
            } else {
                result = 0;
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
        return result;
    }
}
