package com.example.develop.controller;

import com.example.develop.DTO.PaymentDTO;
import com.example.develop.DTO.PaymentDetailDTO;
import com.example.develop.exception.NotFoundException;
import com.example.develop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/payment")
public class RestPayment {
    private PaymentService paymentService;
    @Autowired
    public RestPayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @GetMapping("detail/{payment_id}")
    public List<PaymentDetailDTO> getPaymentIdByIdPayment(@PathVariable int payment_id) {
    List<PaymentDetailDTO> paymentDetailDTOS = paymentService.paymentDetailsByIdPayment(payment_id);
    if (paymentDetailDTOS == null) {
        throw new NotFoundException("not found id");
    } else {
        return paymentDetailDTOS;
    }
}
    @GetMapping("list")
    public List<PaymentDTO> getPaymentByIdUser() {
        return paymentService.getAllPaymentsWithTotalAmount(2);
    }
   @GetMapping("list-filter")
    public List<PaymentDTO> getPaymentFilterDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dateString){
        int idUser =2;
        LocalDate date = LocalDate.parse(dateString+"-01");
        int month = date.getMonthValue();
        int year = date.getYear();
        List<PaymentDTO> paymentDTOS = paymentService.getPaymentFilterDate(month,year,idUser);
        if(paymentDTOS == null){
           throw new NotFoundException("User don't have any payments");
        }
        return paymentDTOS;
    }
}
