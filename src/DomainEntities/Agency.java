package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agency {

    private SimpleIntegerProperty AgencyId;
    private SimpleStringProperty AgncyAddress;
    private SimpleStringProperty AgncyCity;
    private SimpleStringProperty AgncyProv;
    private SimpleStringProperty AgncyPostal;
    private SimpleStringProperty AgncyCountry;
    private SimpleStringProperty AgncyPhone;
    private SimpleStringProperty AgncyFax;

    public Agency() {
    }

    public Agency(int agencyId, String agncyAddress, String agncyCity, String agncyProv, String agncyPostal, String agncyCountry, String agncyPhone, String agncyFax) {
        AgencyId = new SimpleIntegerProperty(agencyId);
        AgncyAddress = new SimpleStringProperty(agncyAddress);
        AgncyCity = new SimpleStringProperty(agncyCity);
        AgncyProv = new SimpleStringProperty(agncyProv);
        AgncyPostal = new SimpleStringProperty(agncyPostal);
        AgncyCountry = new SimpleStringProperty(agncyCountry);
        AgncyPhone = new SimpleStringProperty(agncyPhone);
        AgncyFax = new SimpleStringProperty(agncyFax);
    }

    public int getAgencyId() {
        return AgencyId.get();
    }

    public SimpleIntegerProperty agencyIdProperty() {
        return AgencyId;
    }

    public void setAgencyId(int agencyId) {
        this.AgencyId.set(agencyId);
    }

    public String getAgncyAddress() {
        return AgncyAddress.get();
    }

    public SimpleStringProperty agncyAddressProperty() {
        return AgncyAddress;
    }

    public void setAgncyAddress(String agncyAddress) {
        this.AgncyAddress.set(agncyAddress);
    }

    public String getAgncyCity() {
        return AgncyCity.get();
    }

    public SimpleStringProperty agncyCityProperty() {
        return AgncyCity;
    }

    public void setAgncyCity(String agncyCity) {
        this.AgncyCity.set(agncyCity);
    }

    public String getAgncyProv() {
        return AgncyProv.get();
    }

    public SimpleStringProperty agncyProvProperty() {
        return AgncyProv;
    }

    public void setAgncyProv(String agncyProv) {
        this.AgncyProv.set(agncyProv);
    }

    public String getAgncyPostal() {
        return AgncyPostal.get();
    }

    public SimpleStringProperty agncyPostalProperty() {
        return AgncyPostal;
    }

    public void setAgncyPostal(String agncyPostal) {
        this.AgncyPostal.set(agncyPostal);
    }

    public String getAgncyCountry() {
        return AgncyCountry.get();
    }

    public SimpleStringProperty agncyCountryProperty() {
        return AgncyCountry;
    }

    public void setAgncyCountry(String agncyCountry) {
        this.AgncyCountry.set(agncyCountry);
    }

    public String getAgncyPhone() {
        return AgncyPhone.get();
    }

    public SimpleStringProperty agncyPhoneProperty() {
        return AgncyPhone;
    }

    public void setAgncyPhone(String agncyPhone) {
        this.AgncyPhone.set(agncyPhone);
    }

    public String getAgncyFax() {
        return AgncyFax.get();
    }

    public SimpleStringProperty agncyFaxProperty() {
        return AgncyFax;
    }

    public void setAgncyFax(String agncyFax) {
        this.AgncyFax.set(agncyFax);
    }


    @Override
    public String toString() {
        return getAgncyAddress() + " " + getAgncyCity() + ", " + getAgncyProv();
    }
}
