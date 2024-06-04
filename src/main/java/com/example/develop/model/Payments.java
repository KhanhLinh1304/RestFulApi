package com.example.develop.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="paid_at")
    private Date paid_at;

    @ManyToOne(fetch = FetchType.LAZY , cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private AdminUser adminUser;
    @OneToMany(mappedBy = "payments",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<PaymentDetail> paymentDetails;
    public Payments() {
    }

    public Payments(Date paid_at, AdminUser adminUser, List<PaymentDetail> paymentDetails) {
        this.paid_at = paid_at;
        this.adminUser = adminUser;
        this.paymentDetails = paymentDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Date getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(Date paid_at) {
        this.paid_at = paid_at;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public List<PaymentDetail> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
    public void add(PaymentDetail details){
        if(paymentDetails == null){
            paymentDetails = new ArrayList<>();
        }
        paymentDetails.add(details);
        details.setPayments(this);
    }
}
