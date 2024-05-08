package com.example.demo.BLL;

import com.example.demo.DLL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Refund {

    String CNIC;
    double amount;

    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    public Refund(String CNIC, double amount) {
        this.CNIC = CNIC;
        this.amount = amount;

    }

    public String getCNIC() {
        return CNIC;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }
    public static void deleteRefundRecord(String cnic) {
        String sql = "DELETE FROM refund WHERE CNIC = ?";

        Connection conn = dbConnection.getConnection();
        try
              {
                  PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cnic);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Refund record deleted successfully.");
            } else {
                System.out.println("No record found with CNIC: " + cnic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoDB() {
        String query = "INSERT INTO refund (CNIC, amount) VALUES (?, ?);";
        Connection conn = dbConnection.getConnection();
        try {
                  PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, this.CNIC);
            stmt.setDouble(2, this.amount);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Refund successfully inserted into database.");
            } else {
                System.out.println("Insert operation failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting refund into database.");
            e.printStackTrace();
        }
    }
}
