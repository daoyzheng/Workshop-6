package DataAccessObjects;

import DomainEntities.Supplier;

import java.util.ArrayList;

public interface SuppliersDAO {
    public ArrayList<Supplier> getAllSuppliers();
    public Supplier getSupplierById(int supplierId);
    public void updateSupplier(Supplier oldSupplier, Supplier newSupplier);
    public Supplier addSupplier(Supplier supplier);
    //public boolean deleteSupplier(Supplier supplier);
}
