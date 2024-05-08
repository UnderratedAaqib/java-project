package com.example.demo.BLL;
import com.example.demo.DLL.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Room {
    private int roomNumber;
    private String type;
    private String status;
    private double price;

    private int Capacity;

    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();


    public Room(int roomNumber, String type, String status, double price,int capacity) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = status;
        this.price = price;
        this.Capacity=capacity;

    }

    // Getters and setters for all fields
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double specificPrice(String roomNumber, String noOfSeatsStr) {
        int noOfSeats = Integer.parseInt(noOfSeatsStr);
        double a=0.0;
        String query = "SELECT price FROM rooms WHERE roomNumber = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, roomNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double roomPrice = rs.getDouble("price");
                double totalPrice = roomPrice * noOfSeats;
                a=totalPrice;
                System.out.println("Total price for " + noOfSeats + " seats in room " + roomNumber + ": " + totalPrice);

            } else {
                System.out.println("Room with room number " + roomNumber + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void insertIntoDB() {
        String query = "INSERT INTO rooms (roomNumber, type, status, price, Capacity) VALUES (?, ?, ?, ?, ?);";
        Connection conn = dbConnection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.roomNumber);
            stmt.setString(2, this.type);
            stmt.setString(3, this.status);
            stmt.setDouble(4, this.price);
            stmt.setInt(5, this.Capacity);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Room successfully inserted.");
            } else {
                System.out.println("Insert operation failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting room into database.");
            e.printStackTrace();
        }
    }

}
