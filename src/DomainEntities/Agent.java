/*
Purpose: Domain entity to manage hold agent data
Author:  Stuart Peters
Date: May, 2019
 */


package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {

    private SimpleIntegerProperty agentId;
    private SimpleStringProperty agtFirstName;
    private SimpleStringProperty agtMiddleInitial;
    private SimpleStringProperty agtLastName;
    private SimpleStringProperty agtBusPhone;
    private SimpleStringProperty agtEmail;
    private SimpleStringProperty agtPosition;
    private SimpleStringProperty agtUserName;
    private SimpleStringProperty agtPassword;
    private SimpleIntegerProperty agencyId;


    //constructor

    public Agent() {}

    public Agent(int agentId, String agtFirstName, String agtMiddleInitial, String agtLastName, String agtBusPhone, String agtEmail, String agtPosition, String agtUserName, String agtPassword, int agencyId) {
        this.agentId = new SimpleIntegerProperty(agentId);
        this.agtFirstName = new SimpleStringProperty(agtFirstName);
        this.agtMiddleInitial = new SimpleStringProperty(agtMiddleInitial);
        this.agtLastName = new SimpleStringProperty(agtLastName);
        this.agtBusPhone = new SimpleStringProperty(agtBusPhone);
        this.agtEmail = new SimpleStringProperty(agtEmail);
        this.agtPosition = new SimpleStringProperty(agtPosition);
        this.agtUserName = new SimpleStringProperty(agtUserName);
        this.agtPassword = new SimpleStringProperty(agtPassword);
        this.agencyId = new SimpleIntegerProperty(agencyId);
    }


    //properties
    public int getAgentId() { return agentId.getValue();}
    public void setAgentId(int agentId) { this.agentId.set(agentId); }

    public String getAgtFirstName() { return agtFirstName.getValue(); }
    public void setAgtFirstName(String agtFirstName) { this.agtFirstName.set(agtFirstName); }

    public String getAgtMiddleInitial() { return agtMiddleInitial.getValue(); }
    public void setAgtMiddleInitial(String agtMiddleInitial) { this.agtMiddleInitial.set(agtMiddleInitial); }

    public String getAgtLastName() { return agtLastName.getValue(); }
    public void setAgtLastName(String agtLastName) { this.agtLastName.set(agtLastName); }

    public String getAgtBusPhone() { return agtBusPhone.getValue(); }
    public void setAgtBusPhone(String agtBusPhone) { this.agtBusPhone.set(agtBusPhone); }

    public String getAgtEmail() { return agtEmail.getValue(); }
    public void setAgtEmail(String agtEmail) { this.agtEmail.set(agtEmail); }

    public String getAgtPosition() { return agtPosition.getValue(); }
    public void setAgtPosition(String agtPosition) { this.agtPosition.set(agtPosition); }

    public String getAgtUserName() { return agtUserName.getValue(); }
    public void setAgtUserName(String agtUserName) { this.agtUserName.set(agtUserName); }

    public String getAgtPassword() { return agtPassword.getValue(); }
    public void setAgtPassword(String agtPassword) { this.agtPassword.set(agtPassword); }

    public int getAgencyId() { return agencyId.getValue(); }
    public void setAgencyId(int agencyId) { this.agencyId.set(agencyId); }

    //methods
    @Override
    public String toString() {
        return agtFirstName.get() + " " + agtLastName.get();
    }
}