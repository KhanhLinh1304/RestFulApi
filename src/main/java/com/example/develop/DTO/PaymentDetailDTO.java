package com.example.develop.DTO;

public class PaymentDetailDTO {
    private int id_payment;
    private String name_payment;
    private double amount;

    public PaymentDetailDTO() {
    }

    public PaymentDetailDTO(int id_payment, String name_payment, double amount) {
        this.id_payment = id_payment;
        this.name_payment = name_payment;
        this.amount = amount;
    }

    public int getId_payment() {
        return id_payment;
    }

    public void setId_payment(int id_payment) {
        this.id_payment = id_payment;
    }

    public String getName_payment() {
        return name_payment;
    }

    public void setName_payment(String name_payment) {
        this.name_payment = name_payment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
