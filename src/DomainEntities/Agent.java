/*
Purpose: Domain entity to manage hold agent data
Author:  Stuart Peters
Date: May, 2019
 */


package DomainEntities;

import DataAccessObjects.AgencyManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

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
    private SimpleStringProperty agencyLocation;


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

        String aName = "";
        if (!Objects.equals(agencyId, null)) {
            Agency a = AgencyManager.getAgencyById(getAgencyId());
            aName = a.toString();
        }
        this.agencyLocation = new SimpleStringProperty(aName);
    }


    //properties


    public int getAgentId() {
        return agentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
    }

    public String getAgtFirstName() {
        return agtFirstName.get();
    }

    public SimpleStringProperty agtFirstNameProperty() {
        return agtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        this.agtFirstName.set(agtFirstName);
    }

    public String getAgtMiddleInitial() {
        return agtMiddleInitial.get();
    }

    public SimpleStringProperty agtMiddleInitialProperty() {
        return agtMiddleInitial;
    }

    public void setAgtMiddleInitial(String agtMiddleInitial) {
        this.agtMiddleInitial.set(agtMiddleInitial);
    }

    public String getAgtLastName() {
        return agtLastName.get();
    }

    public SimpleStringProperty agtLastNameProperty() {
        return agtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        this.agtLastName.set(agtLastName);
    }

    public String getAgtBusPhone() {
        return agtBusPhone.get();
    }

    public SimpleStringProperty agtBusPhoneProperty() {
        return agtBusPhone;
    }

    public void setAgtBusPhone(String agtBusPhone) {
        this.agtBusPhone.set(agtBusPhone);
    }

    public String getAgtEmail() {
        return agtEmail.get();
    }

    public SimpleStringProperty agtEmailProperty() {
        return agtEmail;
    }

    public void setAgtEmail(String agtEmail) {
        this.agtEmail.set(agtEmail);
    }

    public String getAgtPosition() {
        return agtPosition.get();
    }

    public SimpleStringProperty agtPositionProperty() {
        return agtPosition;
    }

    public void setAgtPosition(String agtPosition) {
        this.agtPosition.set(agtPosition);
    }

    public String getAgtUserName() {
        return agtUserName.get();
    }

    public SimpleStringProperty agtUserNameProperty() {
        return agtUserName;
    }

    public void setAgtUserName(String agtUserName) {
        this.agtUserName.set(agtUserName);
    }

    public String getAgtPassword() {
        return agtPassword.get();
    }

    public SimpleStringProperty agtPasswordProperty() {
        return agtPassword;
    }

    public void setAgtPassword(String agtPassword) {
        this.agtPassword.set(agtPassword);
    }

    public int getAgencyId() {
        return agencyId.get();
    }

    public SimpleIntegerProperty agencyIdProperty() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId.set(agencyId);
        String aName = "";
        if (!Objects.equals(agencyId, null)) {
            Agency a = AgencyManager.getAgencyById(getAgencyId());
            aName = a.toString();
        }
        this.agencyLocation = new SimpleStringProperty(aName);
    }

    public String getAgencyLocation() {
        return agencyLocation.get();
    }

    public SimpleStringProperty agencyLocationProperty() {
        return agencyLocation;
    }

    public String setAgencyLocation() {
        if (Objects.equals(getAgencyId(), null)) {
            this.agencyLocation.set("");
            return "";
        } else {
            Agency a = AgencyManager.getAgencyById(getAgencyId());
            this.agencyLocation.set(a.toString());
            return a.toString();
        }
    }

    //methods
    @Override
    public String toString() {
        return agtLastName.get() + ", " + agtFirstName.get();
    }


}