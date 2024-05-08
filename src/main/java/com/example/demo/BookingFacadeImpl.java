package com.example.demo;

import com.example.demo.BLL.Booking;
import com.example.demo.DLL.DatabaseConnection;
import com.example.demo.DLL.PaymentDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingFacadeImpl implements BookingFacade {
    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private PaymentDao paymentDao;

    public BookingFacadeImpl(DatabaseConnection dbConnection, PaymentDao paymentDao) {
        //this.dbConnection = dbConnection;
        this.paymentDao = paymentDao;
    }

    @Override
    public boolean confirmBooking(int roomNumber, String cnic, int seats, String phoneNumber) {
        try (Connection conn = dbConnection.getConnection()) {
            // Check and update room capacity
            String updateQuery = "UPDATE rooms SET Capacity = Capacity - ? WHERE roomNumber = ? AND Capacity >= ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, seats);
                updateStmt.setInt(2, roomNumber);
                updateStmt.setInt(3, seats);
                int updateCount = updateStmt.executeUpdate();

                if (updateCount > 0) {
                    Booking booking = new Booking(roomNumber, cnic, seats);
                    booking.insertBookingIntoDB();;  // Assuming this method handles the insertion logic
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
