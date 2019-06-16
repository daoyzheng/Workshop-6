/*
Purpose: Data Access Class for packages
Author:  Hoora
Date: May, 2019
 */

package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.PackageProductSupplier;
import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;

public class PackageProductSupplierManager
{
    //retrieve ProductSupplier sets from database based on PackageID
    public static ObservableList<PackageProductSupplier> getPrdSplByPkgId(int PkgId)
    {
        ObservableList<PackageProductSupplier> PrdSpl = FXCollections.observableArrayList();

        try
        {
            Connection conn = DbConnection.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT packages_products_suppliers.PackageId, " +
                    "packages_products_suppliers.ProductSupplierId, " +
                    "ProdName, SupName FROM packages_products_suppliers " +
                    "INNER JOIN products_suppliers " +
                    "ON packages_products_suppliers.ProductSupplierId = products_suppliers.ProductSupplierId " +
                    "INNER JOIN products " +
                    "ON products_suppliers.ProductId = products.ProductId " +
                    "INNER JOIN suppliers " +
                    "ON products_suppliers.SupplierId = suppliers.SupplierId "+
                    "WHERE PackageId = " + PkgId);

            while (rs.next())
            {
                PrdSpl.add(new PackageProductSupplier(rs.getInt(1), rs.getInt(2),
                        rs.getString(3), rs.getString(4)));
            }
            conn.close(); // should be in finally block?

        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return PrdSpl;
    }


    // insert a PkgPrdSpl record in the database, returns true if successful
    public static boolean insertPkgPrdSpl(int PkgID, int PrdSplId)
    {
        Boolean isSuccess = false;

        try
        {
            Connection conn = DbConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO 'packages_products_suppliers' " +
                    "VALUES (??)");
            stm.setInt(1, PkgID);
            stm.setInt(2, PrdSplId);
            int numRows = stm.executeUpdate();
            if (numRows == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting into the database!", ButtonType.CLOSE);
                alert.showAndWait();
            }
            else
            {
                isSuccess = true;
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return isSuccess;
    }


    // delete a PkgPrdSpl record in the database, returns true if successful
    public static boolean deletePkgPrdSpl(int PkgID, int PrdSplId)
    {
        Boolean isSuccess = false;

        try
        {
            Connection conn = DbConnection.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("DELETE FROM 'packages_products_suppliers' " +
                    "WHERE 'PackageId' = " + PkgID + " AND 'ProductSupplierId' = " + PrdSplId);

            if (rs == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting from the database!", ButtonType.CLOSE);
                alert.showAndWait();
            }
            else
            {
                isSuccess = true;
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return isSuccess;
    }

}