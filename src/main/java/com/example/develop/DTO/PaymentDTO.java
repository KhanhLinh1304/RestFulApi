package com.example.develop.DTO;

import com.example.develop.model.Payments;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDTO {
    private int id;
    private String paidAt;
    private double totalAmount;


    public PaymentDTO() {
    }

    public PaymentDTO(int id, String paidAt, double totalAmount) {
        this.id = id;
        this.paidAt = paidAt;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
