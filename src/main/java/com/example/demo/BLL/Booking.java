package com.example.demo.BLL;

import com.example.demo.DLL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Booking {
    private int id;
    private int roomNumber;
    private String cnic;
    private double price;


    public int getId(){
        return id;
    }
    public int getRoomNumber(){
        return roomNumber;
    }

    public String getCnic(){
        return cnic;
    }
    public double getPrice(){
        return price;
    }

    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();

    public Booking(int roomNumber, String cnic, double price) {
        this.roomNumber = roomNumber;
        this.cnic = cnic;
        this.price = price;

    }

    // Getters and setters...

    public void insertBookingIntoDB() throws SQLException {
        String insertQuery = "INSERT INTO Booking (RoomNo, CNIC, Price) VALUES (?, ?, ?)";

        Connection conn = dbConnection.getConnection();
        PreparedStatement stmt=null;
        try {
            stmt = conn.prepareStatement(insertQuery);
            stmt.setInt(1, roomNumber);
            stmt.setString(2, cnic);
            stmt.setDouble(3, price);
            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("Booking successful!");
            } else {
                System.out.println("Booking failed.");
            }
        } catch (SQLException e) {
            System.err.println("Error making booking: " + e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
