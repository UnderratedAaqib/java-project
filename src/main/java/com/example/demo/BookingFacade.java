package com.example.demo;

public interface BookingFacade {
    boolean confirmBooking(int roomNumber, String cnic, int seats, String phoneNumber);
}

