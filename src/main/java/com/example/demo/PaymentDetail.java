package com.example.demo;
public class PaymentDetail {
    private Integer roomNumber;
    private String cnic;
    private Double amount;
    private String paymentType;

    public PaymentDetail(Integer roomNumber, String cnic, Double amount, String paymentType) {
        this.roomNumber = roomNumber;
        this.cnic = cnic;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    // Getters and setters
    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}

