package com.example.develop.service;

import com.example.develop.DAO.PaymentDAO;
import com.example.develop.DTO.PaymentDTO;
import com.example.develop.DTO.PaymentDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private PaymentDAO paymentDAO;

    @Autowired
    public PaymentServiceImpl(PaymentDAO thePaymentDAO) {
        this.paymentDAO = thePaymentDAO;
    }

    @Override
    public List<PaymentDetailDTO> paymentDetailsByIdPayment(int id_payment) {
        List<Object[]> objects = paymentDAO.getPaymentsByPaymentId(id_payment);
        List<PaymentDetailDTO> paymentDetails = new ArrayList<>();
       for (Object[] result : objects){
           Double amount = (Double) result[0];
           Integer idPayment = (Integer) result[1];
           String namePayment = (String) result[2];
           PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO(idPayment,namePayment,amount);
           paymentDetails.add(paymentDetailDTO);

       }
       if(paymentDetails.size() == 0){
           return null;
       }else{
           return paymentDetails;
       }
    }


    public List<PaymentDTO> getAllPaymentsWithTotalAmount(int userId) {
        List<Object[]> results = paymentDAO.getAllPaymentsWithTotalAmount(userId);
        List<PaymentDTO> payments = new ArrayList<>();
        for (Object[] result : results) {
            Integer paymentId = (Integer) result[0];
            Date paidAt = (Date) result[1];
            Double totalAmount = (Double) result[2];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = formatter.format(paidAt);
            PaymentDTO paymentDTO = new PaymentDTO(paymentId,formatDate, totalAmount);
            payments.add(paymentDTO);
        }

        return payments;
    }

   @Override
    public List<PaymentDTO> getPaymentFilterDate(int month, int year,int idUser) {
        List<Object[]> objects = paymentDAO.getPaymentFilterDate(month,year,idUser);
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for(Object[] result : objects){
            Integer paymentID = (Integer) result[0];
            Date paidAt = (Date) result[1];
            Double totalAmount = (Double) result[2];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = formatter.format(paidAt);
            PaymentDTO paymentDTO = new PaymentDTO(paymentID,formatDate,totalAmount);
            paymentDTOS.add(paymentDTO);
        }
        if(paymentDTOS.size() == 0){
            return null;
        }else{
            return paymentDTOS;
        }

    }
}
