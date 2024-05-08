package com.example.demo.BLL;


import com.example.demo.PaymentStrategy;

public class MobilePayment implements PaymentStrategy {
    private String mobileNumber;

    public MobilePayment(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public void pay(double amount) {
        if (validateMobileNumber()) {
            System.out.println("Paid " + amount + " using Mobile Payment. Mobile Number: " + mobileNumber);
            // Logic to process mobile payment
        } else {
            System.out.println("Mobile number validation failed.");
        }
    }

    private boolean validateMobileNumber() {
        // Add realistic validation for mobile number
        return mobileNumber != null && !mobileNumber.isEmpty();
    }
}

