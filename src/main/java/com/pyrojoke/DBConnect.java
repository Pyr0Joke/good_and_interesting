package com.pyrojoke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String DB_URL = "DB_URL";
    private static final String USER = "USER";
    private static final String PASS = "PASSWORD";

    public static Connection connectToBD(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
        } else {
            System.out.println("Failed to make connection to database");
        }
        return connection;
    }



}
