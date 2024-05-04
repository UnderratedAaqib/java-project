package com.example.demo;

public class Room {
    private int roomNumber;
    private String type;
    private String status;
    private double price;

    // Constructor
    public Room(int roomNumber, String type, String status, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    // Getters and setters
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

    // Additional methods
    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}
