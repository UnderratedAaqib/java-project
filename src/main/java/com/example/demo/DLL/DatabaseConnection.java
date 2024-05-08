package com.example.demo.DLL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Assume this is how you set up your connection.
            // Adjust the connection details as necessary.
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
        } catch (SQLException e) {
            System.out.println("Database connection creation failed.");
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            // Check if connection is valid or not closed, reconnect if necessary
            if (connection == null || connection.isClosed()) {
                // Re-establish the connection
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
            }
        } catch (SQLException e) {
            System.out.println("Failed to re-establish the database connection.");
            e.printStackTrace();
        }
        return connection;
    }
}
