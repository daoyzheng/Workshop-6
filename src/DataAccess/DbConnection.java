/*
Purpose: Get database connection
Author:  Dao Zheng
Date: May, 2019
 */

package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnection {
    private static final String DBNAME = "travelexperts";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Find database driver
        Class.forName("com.mysql.jdbc.Driver");
        // Get database connection using DriverManager
        return DriverManager.getConnection(URL+DBNAME,USER,PASSWORD);
    }
}
