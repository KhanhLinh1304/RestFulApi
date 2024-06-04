package com.example.develop.DAO;

import com.example.develop.model.PaymentDetail;
import com.example.develop.model.Payments;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDAOImpl implements  PaymentDAO{
    private EntityManager entityManager;
    public PaymentDAOImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;

    }
    @Override
    public List<Object[]> getPaymentsByPaymentId(int payment_id) {
        TypedQuery<Object[]> theQuery = entityManager.createQuery(
               "select pd.amount,pd.payments.id,pd.detail_name from PaymentDetail pd where pd.payments.id = :data",Object[].class
        );
        theQuery.setParameter("data",payment_id);
        List<Object[]> paymentDetails = theQuery.getResultList();
        return paymentDetails;
    }

    @Override
    public List<Object[]> getAllPaymentsWithTotalAmount(int userId) {
        TypedQuery<Object[]> theQuery = entityManager.createQuery(
                "select p.id, p.paid_at, sum(pd.amount) as totalAmount from Payments p join p.paymentDetails pd where p.adminUser.id = :theData group by p.id ",Object[].class);
                    theQuery.setParameter("theData",userId);

        return theQuery.getResultList();
    }

    @Override
    public List<Object[]> getPaymentFilterDate(int month, int year, int idUser) {
        TypedQuery<Object[]> theQuery = entityManager.createQuery("select p.id, p.paid_at, sum(pd.amount) from Payments p join p.paymentDetails pd where month(p.paid_at) = :month AND year(p.paid_at) = :year AND p.adminUser.id = :dataUser group by p.id ",Object[].class);
        theQuery.setParameter("month",month);
        theQuery.setParameter("year",year);
        theQuery.setParameter("dataUser",idUser);
        return theQuery.getResultList();
    }
}
