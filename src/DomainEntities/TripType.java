package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TripType {
    private SimpleStringProperty tripTypeId;
    private SimpleStringProperty tTName;

    // constructor
    public TripType(String tripTypeId, String tTName) {
        this.tripTypeId = new SimpleStringProperty(tripTypeId);
        this.tTName = new SimpleStringProperty(tTName);
    }

    // getter & setter
    public String getTripTypeId() {
        return tripTypeId.get();
    }

    public SimpleStringProperty tripTypeIdProperty() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId.set(tripTypeId);
    }

    public String gettTName() {
        return tTName.get();
    }

    public SimpleStringProperty tTNameProperty() {
        return tTName;
    }

    public void settTName(String tTName) {
        this.tTName.set(tTName);
    }

    // overrides

    @Override
    public String toString() {
        return tTName.get();
    }
}
