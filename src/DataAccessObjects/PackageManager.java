/*
Purpose: Data Access Class for packages
Author:  Hoora
Date: May-June, 2019
 */

package DataAccessObjects;

import DataAccess.DbConnection;
import DomainEntities.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.*;

public class PackageManager {

    //retrieve all packages from the database and populates package objects in a list
    public static ObservableList<Package> getAllPackages()
    {
        ObservableList<Package> packages = FXCollections.observableArrayList();

        try
        {
            Connection conn = DbConnection.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM packages");
            while (resultSet.next())
            {
                Package pkg = new Package();
                pkg.setPackageId(resultSet.getInt(1));

                pkg.setPkgName(resultSet.getString(2));

                if (resultSet.getDate(3) != null)
                    pkg.setPkgStartDate(resultSet.getDate(3).toLocalDate());
                else
                    pkg.setPkgStartDate(null);

                if (resultSet.getDate(4) != null)
                    pkg.setPkgEndDate(resultSet.getDate(4).toLocalDate());
                else
                    pkg.setPkgEndDate(null);

                if (resultSet.getString(5) != null)
                    pkg.setPkgDesc(resultSet.getString(5));
                else
                    pkg.setPkgDesc(null);

                pkg.setPkgBasePrice(resultSet.getDouble(6));

                pkg.setPkgAgencyCommission(resultSet.getDouble(7));

                pkg.setActive(resultSet.getBoolean(8));

                packages.add(pkg);
            }
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return packages;
    }


    //retrieve a package from database based on ID
    public static Package getPackageById(int id)
    {
        Package pkg = null;

        try
        {
            Connection conn = DbConnection.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM packages WHERE packageId = " + id);
            while (resultSet.next())
            {
                pkg.setPackageId(resultSet.getInt(1));

                pkg.setPkgName(resultSet.getString(2));

                if (resultSet.getDate(3) != null)
                    pkg.setPkgStartDate(resultSet.getDate(3).toLocalDate());
                else
                    pkg.setPkgStartDate(null);

                if (resultSet.getDate(4) != null)
                    pkg.setPkgEndDate(resultSet.getDate(4).toLocalDate());
                else
                    pkg.setPkgEndDate(null);

                if (resultSet.getString(5) != null)
                    pkg.setPkgDesc(resultSet.getString(5));
                else
                    pkg.setPkgDesc(null);

                pkg.setPkgBasePrice(resultSet.getDouble(6));

                pkg.setPkgAgencyCommission(resultSet.getDouble(7));

                pkg.setActive(resultSet.getBoolean(8));
            }
            conn.close();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pkg;
    }


    //update a package record in the database, returns true if successful
    public static boolean updatePackage(Package newPackage, Package oldPackage)
    {
        Boolean isSuccess = false;
        try
        {
            Connection conn = DbConnection.getConnection();
            PreparedStatement prepStatement = conn.prepareStatement(
                    "UPDATE packages SET PkgName=?, " +
                            "PkgStartDate=?," +
                            "PkgEndDate=?," +
                            "PkgDesc=?," +
                            "PkgBasePrice=?," +
                            "PkgAgencyCommission=?," +
                            "Active=? WHERE PackageId=?");

            prepStatement.setString(1,newPackage.getPkgName());

            if (newPackage.getPkgStartDate() != null)
                prepStatement.setDate(2, Date.valueOf(newPackage.getPkgStartDate()));
            else
                prepStatement.setDate(2, null);

            if (newPackage.getPkgEndDate() != null)
                prepStatement.setDate(3, Date.valueOf(newPackage.getPkgEndDate()));
            else
                prepStatement.setDate(3, null);

            if (newPackage.getPkgDesc() != null)
                prepStatement.setString(4, newPackage.getPkgDesc());
            else
                prepStatement.setString(4, null);

            prepStatement.setDouble(5, newPackage.getPkgBasePrice());
            prepStatement.setDouble(6, newPackage.getPkgAgencyCommission());
            prepStatement.setBoolean(7, newPackage.isActive());
            prepStatement.setInt(8, oldPackage.getPackageId());

            int numRows = prepStatement.executeUpdate();
            if (numRows == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error updating the database!", ButtonType.CLOSE);
                alert.showAndWait();
            }
            else
            {
                isSuccess = true;
            }
            conn.close();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }


    //insert a package in the database, returns true if successful
    public static int insertPackage (Package pkg)
    {
        int newPkgId = 0;

        try
        {
            Connection conn = DbConnection.getConnection();
            PreparedStatement prepStatement = conn.prepareStatement(
                    "INSERT INTO packages (PkgName, PkgStartDate, PkgEndDate, PkgDesc, " +
                            "PkgBasePrice, PkgAgencyCommission, Active) " +
                            "VALUES (?,?,?,?,?,?,?)");
            prepStatement.setString(1, pkg.getPkgName());

            if (pkg.getPkgStartDate() != null)
                prepStatement.setDate(2, Date.valueOf(pkg.getPkgStartDate()));
            else
                prepStatement.setDate(2, null);

            if (pkg.getPkgEndDate() != null)
                prepStatement.setDate(3, Date.valueOf(pkg.getPkgEndDate()));
            else
                prepStatement.setDate(3, null);

            if (pkg.getPkgDesc() != null)
                prepStatement.setString(4, pkg.getPkgDesc());
            else
                prepStatement.setString(4, null);

            prepStatement.setDouble(5, pkg.getPkgBasePrice());

            prepStatement.setDouble(6, pkg.getPkgAgencyCommission());

            prepStatement.setBoolean(7, pkg.isActive());

            int numRows = prepStatement.executeUpdate();
            if (numRows == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting into the database!", ButtonType.CLOSE);
                alert.showAndWait();
            }
            else
            {
                //get the id of the new package added to the database
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT MAX(PackageId) FROM packages");

                while (rs.next())
                {
                    newPkgId = rs.getInt(1);
                }
            }
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return newPkgId;
    }
}