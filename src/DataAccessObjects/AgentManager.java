package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Agent;
import javafx.scene.control.Alert;

import javax.lang.model.element.NestingKind;
import java.sql.*;
import java.util.ArrayList;

public class AgentManager {

    //retrieve all Agents from the database and populates Agent objects in a list
    public static ArrayList<Agent> getAllAgents(){

        ArrayList<Agent> list = new ArrayList<>();

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();

            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT * FROM agents");

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                list.add(new Agent(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getInt(10)));
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
    public static Agent getAgentById(int id) {
        Agent agt = null;

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();
            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT * FROM agents WHERE AgentId=" + id);

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                agt = new Agent(
                        Integer.parseInt(rs.getString(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        Integer.parseInt(rs.getString(10)));
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
            return agt;
        }
    }

    //add a new Agent to the database, returns true if successful
    public static int addAgent(Agent newAgent) throws SQLException, ClassNotFoundException {
        int newAgtId = 0;

        String sql = "INSERT INTO agents (" +
                "AgtFirstName, " +
                "AgtMiddleInitial, " +
                "AgtLastName, " +
                "AgtBusPhone, " +
                "AgtEmail, " +
                "AgtPosition, " +
                "AgtUsername, " +
                "AgtPassword, " +
                "AgencyId) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,newAgent.getAgtFirstName());
            stmt.setString(2,newAgent.getAgtMiddleInitial());
            stmt.setString(3,newAgent.getAgtLastName());
            stmt.setString(4,newAgent.getAgtBusPhone());
            stmt.setString(5,newAgent.getAgtEmail());
            stmt.setString(6,newAgent.getAgtPosition());
            stmt.setString(7,newAgent.getAgtUserName());
            stmt.setString(8,newAgent.getAgtPassword());
            stmt.setInt(9,newAgent.getAgencyId());


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
                ResultSet rs = stmt.executeQuery("SELECT MAX(AgentId) FROM agents");

                while (rs.next()) {
                    newAgtId = rs.getInt(1);
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
        return newAgtId;
    }


    //update an Agent record in the database, returns true if successful
    public static boolean updateAgent(Agent changedAgent) throws SQLException, ClassNotFoundException {

        String sql = "update Agents " +
                "set AgtFirstName=?, " +
                "AgtMiddleInitial=?, " +
                "AgtLastName=?, " +
                "AgtBusPhone=?, " +
                "AgtEmail=?, " +
                "AgtPosition=?, " +
                "AgtUsername=?, " +
                "AgtPassword=?, " +
                "AgencyId=? " +
                "where AgentId=?";

        boolean result = false;
        try {

            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,changedAgent.getAgtFirstName());
            stmt.setString(2,changedAgent.getAgtMiddleInitial());
            stmt.setString(3,changedAgent.getAgtLastName());
            stmt.setString(4,changedAgent.getAgtBusPhone());
            stmt.setString(5,changedAgent.getAgtEmail());
            stmt.setString(6,changedAgent.getAgtPosition());
            stmt.setString(7,changedAgent.getAgtUserName());
            stmt.setString(8,changedAgent.getAgtPassword());
            stmt.setInt(9,changedAgent.getAgencyId());
            stmt.setInt(10,changedAgent.getAgentId());

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

    //check if a username is unique value
    public static int checkAgentUsername(String username) {
        int result = 0;

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();
            //create statement
            Statement stmt = conn.createStatement();
            String sql = "SELECT COUNT(AgtUserName) As rowcount FROM agents WHERE AgtUserName='" + username + "'";

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
