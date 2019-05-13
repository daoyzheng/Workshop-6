package DataAccessObjects;

import DomainEntities.Customer;

import java.util.ArrayList;

public class CustomerManager {

    //retrieve all Customers from the database and populates Customer objects in a list
    public static ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> list = new ArrayList<>();

        //create DB connection


        //create statement


        //execute statement

        //enumerate query result and populate list of objects




        return list;
    }

    //retrieve a customer from database based on ID
    public static Customer getCustomerById(int id) {
        Customer a = new Customer();

        return a;


    }


    //add a new Customer to the database, returns true if successful
    public static boolean addCustomer(Customer Customer) {
        boolean result = true;

        return result;
    }


    //update an Customer record in the database, returns true if successful
    public static boolean updateCustomer(){
        boolean result = true;

        return result;
    }



}
