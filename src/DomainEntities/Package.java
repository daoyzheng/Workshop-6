/*
Purpose: Domain entity class for packages
Author:  Hoora
Date: May, 2019
 */

package DomainEntities;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.sql.Date;

public class Package {


    // properties:
    private SimpleIntegerProperty packageId;
    private SimpleStringProperty pkgName;
    private ObjectProperty<Date> pkgStartDate;
    private ObjectProperty<Date> pkgEndDate;
    private SimpleStringProperty pkgDesc;
    private SimpleDoubleProperty pkgBasePrice;
    private SimpleDoubleProperty pkgAgencyCommission;
    private SimpleBooleanProperty active;


    // constructors:
    public Package() {
    }

    public Package(int packageId, String pkgName, Date pkgStartDate, Date pkgEndDate,
                   String pkgDesc, Double pkgBasePrice, Double pkgAgencyCommission,
                   Boolean active) {
        this.packageId.set(packageId);
        this.pkgName.set(pkgName);
        this.pkgStartDate.set(pkgStartDate);
        this.pkgEndDate.set(pkgEndDate);
        this.pkgDesc.set(pkgDesc);
        this.pkgBasePrice.set(pkgBasePrice);
        this.pkgAgencyCommission.set(pkgAgencyCommission);
        this.active.set(active);
    }


    // getters-setters:
    public int getPackageId() {
        return packageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

    public String getPkgName() {
        return pkgName.get();
    }

    public SimpleStringProperty pkgNameProperty() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName.set(pkgName);
    }

    public Date getPkgStartDate() {
        return pkgStartDate.get();
    }

    public ObjectProperty<Date> pkgStartDateProperty() {
        return pkgStartDate;
    }

    public void setPkgStartDate(Date pkgStartDate) {
        this.pkgStartDate.set(pkgStartDate);
    }

    public Date getPkgEndDate() {
        return pkgEndDate.get();
    }

    public ObjectProperty<Date> pkgEndDateProperty() {
        return pkgEndDate;
    }

    public void setPkgEndDate(Date pkgEndDate) {
        this.pkgEndDate.set(pkgEndDate);
    }

    public String getPkgDesc() {
        return pkgDesc.get();
    }

    public SimpleStringProperty pkgDescProperty() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc.set(pkgDesc);
    }

    public double getPkgBasePrice() {
        return pkgBasePrice.get();
    }

    public SimpleDoubleProperty pkgBasePriceProperty() {
        return pkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.pkgBasePrice.set(pkgBasePrice);
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission.get();
    }

    public SimpleDoubleProperty pkgAgencyCommissionProperty() {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.pkgAgencyCommission.set(pkgAgencyCommission);
    }

    public boolean isActive() { //???????
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }


    // toString:
    @Override
    public String toString() {
        return "Package{" +
                "packageId=" + packageId +
                ", pkgName=" + pkgName +
                ", pkgStartDate=" + pkgStartDate +
                ", pkgEndDate=" + pkgEndDate +
                ", pkgDesc=" + pkgDesc +
                ", pkgBasePrice=" + pkgBasePrice +
                ", pkgAgencyCommission=" + pkgAgencyCommission +
                ", active=" + active +
                '}';
    }
}