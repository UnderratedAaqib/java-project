package com.example.demo;



public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        if (validateCardDetails()) {
            System.out.println("Paid " + amount + " using Credit Card. Card Number: " + cardNumber);
            // Logic to process credit card payment
        } else {
            System.out.println("Credit Card validation failed.");
        }
    }

    private boolean validateCardDetails() {
        // Add realistic validation for card number, expiry date, and CVV
        return cardNumber != null && !cardNumber.isEmpty() && expiryDate != null && !expiryDate.isEmpty() && cvv != null && !cvv.isEmpty();
    }
}

