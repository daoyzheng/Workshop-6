/*
Purpose: Domain entity class for Packages
Author:  Hoora
Date: May, 2019
 */

package DomainEntities;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Package {


    // properties:
    private SimpleIntegerProperty packageId;
    private SimpleStringProperty pkgName;
    private SimpleObjectProperty<LocalDate> pkgStartDate;
    private SimpleObjectProperty<LocalDate> pkgEndDate;
    private SimpleStringProperty pkgDesc;
    private SimpleDoubleProperty pkgBasePrice;
    private SimpleDoubleProperty pkgAgencyCommission;
    private SimpleBooleanProperty active;


    // constructors:
    public Package() {
        this.packageId = new SimpleIntegerProperty();
        this.pkgName = new SimpleStringProperty();
        this.pkgStartDate = new SimpleObjectProperty<>();
        this.pkgEndDate = new SimpleObjectProperty<>();
        this.pkgDesc = new SimpleStringProperty();
        this.pkgBasePrice = new SimpleDoubleProperty();
        this.pkgAgencyCommission = new SimpleDoubleProperty();
        this.active = new SimpleBooleanProperty();
    }

    public Package(int packageId, String pkgName, LocalDate pkgStartDate, LocalDate pkgEndDate,
                   String pkgDesc, Double pkgBasePrice, Double pkgAgencyCommission,
                   Boolean active) {


        this.packageId = new SimpleIntegerProperty(packageId);
        this.pkgName = new SimpleStringProperty(pkgName);
        this.pkgStartDate = new SimpleObjectProperty<>(pkgStartDate);
        this.pkgEndDate = new SimpleObjectProperty<>(pkgEndDate);
        this.pkgDesc = new SimpleStringProperty(pkgDesc);
        this.pkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.pkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
        this.active = new SimpleBooleanProperty(active);
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

    public LocalDate getPkgStartDate() {
        return pkgStartDate.get();
    }

    public SimpleObjectProperty<LocalDate> pkgStartDateProperty() {
        return pkgStartDate;
    }

    public void setPkgStartDate(LocalDate pkgStartDate) {
        this.pkgStartDate = new SimpleObjectProperty<>(pkgStartDate);
    }

    public LocalDate getPkgEndDate() {
        return pkgEndDate.get();
    }

    public SimpleObjectProperty<LocalDate> pkgEndDateProperty() {
        return pkgEndDate;
    }

    public void setPkgEndDate(LocalDate pkgEndDate) {
        this.pkgEndDate = new SimpleObjectProperty<>(pkgEndDate);
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

    public void setPkgBasePrice(Double pkgBasePrice) {
        this.pkgBasePrice.set(pkgBasePrice);
    }

    public Double getPkgAgencyCommission() {
        return pkgAgencyCommission.get();
    }

    public SimpleDoubleProperty pkgAgencyCommissionProperty() {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(Double pkgAgencyCommission) {
        this.pkgAgencyCommission.set(pkgAgencyCommission);
    }

    public boolean isActive() {
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
