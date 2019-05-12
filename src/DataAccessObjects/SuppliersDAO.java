package DataAccessObjects;

import DomainEntities.Supplier;

import java.util.ArrayList;

public interface SuppliersDAO {
    ArrayList<Supplier> getAllSuppliers();
    Supplier getSupplierById(int supplierId);
    void updateSupplier(Supplier oldSupplier, Supplier newSupplier);
    Supplier addSupplier(Supplier supplier);
    // boolean deleteSupplier(Supplier supplier);
}
