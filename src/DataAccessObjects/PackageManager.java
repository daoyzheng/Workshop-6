/*
Purpose: Data Access Class for packages
Author:  Hoora
Date: May, 2019
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

        try {
            Connection conn = DbConnection.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 'packages'");
            while (resultSet.next())
            {
                packages.add(new Package(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getString(3),
                        resultSet.getDouble(3),
                        resultSet.getDouble(3),
                        resultSet.getBoolean(3)));
            }
            conn.close();// should be in finally block?
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 'packages' WHERE packageId = " + id);
            while (resultSet.next())
            {
                pkg.setPackageId(resultSet.getInt(1));
                pkg.setPkgName(resultSet.getString(2));
                pkg.setPkgStartDate(resultSet.getDate(3).toLocalDate());
                pkg.setPkgEndDate(resultSet.getDate(4).toLocalDate());
                pkg.setPkgDesc(resultSet.getString(5));
                pkg.setPkgBasePrice(resultSet.getDouble(6));
                pkg.setPkgAgencyCommission(resultSet.getDouble(7));
                pkg.setActive(resultSet.getBoolean(8));
            }
            conn.close(); // should be in finally block?
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
                    "UPDATE 'packages' SET `PkgName`=?, " +
                            "`PkgStartDate`=?," +
                            "`PkgEndDate`=?," +
                            "`PkgDesc`=?," +
                            "`PkgBasePrice`=?," +
                            "`PkgAgencyCommission`=?," +
                            "`Active`=? WHERE `PackageId`=?");
            prepStatement.setString(1,newPackage.getPkgName());
            prepStatement.setDate(2, Date.valueOf(newPackage.getPkgStartDate()));
            prepStatement.setDate(3, Date.valueOf(newPackage.getPkgEndDate()));
            prepStatement.setString(4, newPackage.getPkgDesc());
            prepStatement.setDouble(5,newPackage.getPkgBasePrice());
            prepStatement.setDouble(6,newPackage.getPkgAgencyCommission());
            prepStatement.setBoolean(7, newPackage.isActive());
            prepStatement.setInt(8,oldPackage.getPackageId());

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
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }


    //insert a package in the database, returns true if successful
    public static boolean insertPackage (Package pkg)
    {
        Boolean isSuccessful = false;

        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement prepStatement = conn.prepareStatement(
                    "INSERT INTO 'packages' VALUES (???????)", Statement.RETURN_GENERATED_KEYS);
            prepStatement.setString(1,pkg.getPkgName());
            prepStatement.setDate(2, Date.valueOf(pkg.getPkgStartDate()));
            prepStatement.setDate(3, Date.valueOf(pkg.getPkgEndDate()));
            prepStatement.setString(4, pkg.getPkgDesc());
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
                isSuccessful = true;
            }

        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return isSuccessful;
    }
}