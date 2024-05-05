package com.example.demo;
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


    private DatabaseConnection dbConnection;
    public Room(int roomNumber, String type, String status, double price,int capacity) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = status;
        this.price = price;
        this.Capacity=capacity;
        this.dbConnection=new DatabaseConnection();
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
}
