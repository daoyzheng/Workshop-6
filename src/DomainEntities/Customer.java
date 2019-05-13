/*
Purpose: Domain entity to manage hold customer data
Author:  Stuart Peters
Date: May, 2019
 */


package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Customer {

    //class members/variables
    private SimpleIntegerProperty customerId;
    private SimpleStringProperty custFirstName;
    private SimpleStringProperty custLastName;
    private SimpleStringProperty custAddress;
    private SimpleStringProperty custCity;
    private SimpleStringProperty custProv;
    private SimpleStringProperty custPostal;
    private SimpleStringProperty custCountry;
    private SimpleStringProperty custHomePhone;
    private SimpleStringProperty custBusPhone;
    private SimpleStringProperty custEmail;
    private SimpleStringProperty custUserName;
    private SimpleStringProperty custPassword;
    private SimpleIntegerProperty agentId;


    //constructors

    public Customer(){}

    public Customer(int customerId, String custFirstName, String custLastName, String custAddress, String custCity, String custProv, String custPostal, String custCountry, String custHomePhone, String custBusPhone, String custEmail, String custUserName, String custPassword, int agentId, int packageId) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.custFirstName = new SimpleStringProperty(custFirstName);
        this.custLastName = new SimpleStringProperty(custLastName);
        this.custAddress = new SimpleStringProperty(custAddress);
        this.custCity = new SimpleStringProperty(custCity);
        this.custProv = new SimpleStringProperty(custProv);
        this.custPostal = new SimpleStringProperty(custPostal);
        this.custCountry = new SimpleStringProperty(custCountry);
        this.custHomePhone = new SimpleStringProperty(custHomePhone);
        this.custBusPhone = new SimpleStringProperty(custBusPhone);
        this.custEmail = new SimpleStringProperty(custEmail);
        this.custUserName = new SimpleStringProperty(custUserName);
        this.custPassword = new SimpleStringProperty(custPassword);
        this.agentId = new SimpleIntegerProperty(agentId);
    }

    //properties
    public int getCustomerId() { return customerId.getValue(); }
    public void setCustomerId(int customerId) { this.customerId.set(customerId); }

    public String getCustFirstName() { return custFirstName.getValue(); }
    public void setCustFirstName(String custFirstName) {this.custFirstName.set(custFirstName); }

    public String getCustLastName() { return custLastName.getValue();}
    public void setCustLastName(String custLastName) {this.custLastName.set(custLastName); }

    public String getCustAddress() {return custAddress.getValue(); }
    public void setCustAddress(String custAddress) {this.custAddress.set(custAddress); }

    public String getCustCity() { return custCity.getValue();}
    public void setCustCity(String custCity) { this.custCity.set(custCity); }

    public String getCustProv() { return custProv.getValue(); }
    public void setCustProv(String custProv) { this.custProv.set(custProv); }

    public String getCustPostal() { return custPostal.getValue(); }
    public void setCustPostal(String custPostal) { this.custPostal.set(custPostal); }

    public String getCustCountry() { return custCountry.getValue(); }
    public void setCustCountry(String custCountry) { this.custCountry.set(custCountry); }

    public String getCustHomePhone() { return custHomePhone.getValue(); }
    public void setCustHomePhone(String custHomePhone) { this.custHomePhone.set(custHomePhone); }

    public String getCustBusPhone() { return custBusPhone.getValue(); }
    public void setCustBusPhone(String custBusPhone) { this.custBusPhone.set(custBusPhone); }

    public String getCustEmail() { return custEmail.getValue(); }
    public void setCustEmail(String custEmail) { this.custEmail.set(custEmail); }

    public String getCustUserName() { return custUserName.getValue(); }
    public void setCustUserName(String custUserName) { this.custUserName.set(custUserName); }

    public String getCustPassword() { return custPassword.getValue(); }
    public void setCustPassword(String custPassword) { this.custPassword.set(custPassword); }

    public int getAgentId() { return agentId.getValue(); }
    public void setAgentId(int agentId) { this.agentId.set(agentId); }


    //methods
    @Override
    public String toString() {
        return custFirstName + " " + custLastName;
    }
}





