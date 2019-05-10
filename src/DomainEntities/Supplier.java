package DomainEntities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private SimpleIntegerProperty supplierId;
    private SimpleStringProperty supName;

    public Supplier(int supplierId, String supName) {
        this.supplierId = new SimpleIntegerProperty(supplierId);
        this.supName = new SimpleStringProperty(supName);
    }

    @Override
    public String toString() {
        return supName.getValue();
    }

    public int getSupplierId() {
        return supplierId.getValue();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public String getSupName() {
        return supName.getValue();
    }

    public void setSupName(String supName) {
        this.supName.set(supName);
    }
}
