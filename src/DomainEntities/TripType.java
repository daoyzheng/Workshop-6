package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TripType {
    private SimpleIntegerProperty tripTypeId;
    private SimpleStringProperty tTName;

    // constructor
    public TripType(Integer tripTypeId, String tTName) {
        this.tripTypeId = new SimpleIntegerProperty(tripTypeId);
        this.tTName = new SimpleStringProperty(tTName);
    }

    // getter & setter
    public int getTripTypeId() {
        return tripTypeId.get();
    }

    public SimpleIntegerProperty tripTypeIdProperty() {
        return tripTypeId;
    }

    public void setTripTypeId(int tripTypeId) {
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
}
