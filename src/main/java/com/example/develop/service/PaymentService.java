package com.example.develop.service;

import com.example.develop.DTO.PaymentDTO;
import com.example.develop.DTO.PaymentDetailDTO;
import com.example.develop.model.PaymentDetail;

import java.util.List;

public interface PaymentService {
    List<PaymentDetailDTO> paymentDetailsByIdPayment(int id_payment);
    public List<PaymentDTO> getAllPaymentsWithTotalAmount(int userId);
    public List<PaymentDTO> getPaymentFilterDate(int month, int year, int idUser);
}
