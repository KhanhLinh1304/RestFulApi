package com.example.develop.DAO;

import com.example.develop.model.PaymentDetail;
import com.example.develop.model.Payments;

import java.util.List;

public interface PaymentDAO {
    List<Object[]> getPaymentsByPaymentId(int payment_id);
    List<Object[]> getAllPaymentsWithTotalAmount(int userId);
    List<Object[]> getPaymentFilterDate(int month, int year,int idUser);
}
