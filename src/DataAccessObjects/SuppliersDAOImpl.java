package DataAccessObjects;

import DomainEntities.Supplier;

import java.util.ArrayList;

public class SuppliersDAOImpl implements SuppliersDAO {

    @Override
    public ArrayList<Supplier> getAllSuppliers() {
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();

        return supplierArrayList;
    }

    @Override
    public Supplier getSupplierById(int supplierId) {
        Supplier supplier = null;
        return supplier;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {

    }

    @Override
    public boolean deleteSupplier(Supplier supplier) {

    }
}
