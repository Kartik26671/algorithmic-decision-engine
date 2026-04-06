package com.ade.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/delivery_db",
                    "root",
                    "kartikVishwakarma"
            );

            System.out.println("Connected to DB");
            return conn;

        } catch (Exception e) {
            System.out.println("DB Connection Failed");
            e.printStackTrace();
            return null;
        }
    }
}