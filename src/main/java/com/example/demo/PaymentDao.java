package com.example.demo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDao {
    private DatabaseConnection dbConnection;

    public PaymentDao(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void logPayment(double amount, String paymentType) {
        String sql = "INSERT INTO payments (amount, paymentType, status) VALUES (?, ?, 'Processed')";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, paymentType);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No payment was logged, check insertion logic.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing insert: " + e.getMessage());
        }
    }
}
