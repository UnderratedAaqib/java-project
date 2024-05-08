package com.example.demo.BLL;


import com.example.demo.PaymentStrategy;

public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " with cash.");
        // Logic to handle cash payment could be just a confirmation
    }
}
