package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Agent;

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
        Agent a = new Agent();

        try {
            //create DB connection
            Connection conn =  DbConnection.getConnection();
            //create statement
            Statement stmt = conn.createStatement();

            //query database
            ResultSet rs = stmt.executeQuery("SELECT * FROM agents WHERE AgentId=" + id);

            //create Array list to capture data from DB query rs
            while (rs.next()) {
                a.setAgentId(rs.getInt(1));
                a.setAgtFirstName(rs.getString(2));
                a.setAgtMiddleInitial(rs.getString(3));
                a.setAgtLastName(rs.getString(4));
                a.setAgtBusPhone(rs.getString(5));
                a.setAgtEmail(rs.getString(6));
                a.setAgtPosition(rs.getString(7));
                a.setAgtUserName(rs.getString(8));
                a.setAgtPassword(rs.getString(9));
                a.setAgencyId(rs.getInt(10));
            }
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        finally {
            return a;
        }

    }


    //add a new Agent to the database, returns true if successful
    public static boolean addAgent(Agent agent) {
        boolean result = true;


        try {
            //create connection
            Connection conn = DbConnection.getConnection();



        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }


    //update an Agent record in the database, returns true if successful
    public static boolean updateAgent(Agent oldAgent, Agent newAgent){
        boolean result = true;



        return result;
    }


}
