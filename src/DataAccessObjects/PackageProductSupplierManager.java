/*
Purpose: Data Access Class for packages
Author:  Hoora
Date: May, 2019
 */

package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.PackageProductSupplier;
import javafx.beans.binding.When;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;

public class PackageProductSupplierManager
{
    //retrieve a PackageProductSupplier from database based on PackageID
    public static PackageProductSupplier getPrdSplByPkgId(int PkgId)
    {
        PackageProductSupplier PkgPrdSpl = null;

        try
        {
            Connection conn = DbConnection.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM 'packages_products_suppliersHide' WHERE PackageId = " + PkgId);

            while (rs.next())
            {
                PkgPrdSpl.setPackageId(rs.getInt(1));
                PkgPrdSpl.setProductSupplierId(rs.getInt(2));
            }
            conn.close(); // should be in finally block?

        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return PkgPrdSpl;
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

}