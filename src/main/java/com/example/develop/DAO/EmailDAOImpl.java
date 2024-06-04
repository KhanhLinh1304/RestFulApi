package com.example.develop.DAO;

import com.example.develop.exception.NotFoundException;
import com.example.develop.model.EmailAuthentication;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailDAOImpl implements EmailDAO{
    EntityManager entityManager;
    public EmailDAOImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }
    @Transactional
    @Override
    public void saveToken(EmailAuthentication emailAuthentication) {
         entityManager.persist(emailAuthentication);
    }

    @Override
    public EmailAuthentication findEmailByToken(String token) {

        TypedQuery<EmailAuthentication> mailQuery = entityManager
                .createQuery("select e from EmailAuthentication e where e.token = :token", EmailAuthentication.class);

        mailQuery.setParameter("token", token);

        EmailAuthentication emailAuthentication;
        try{
             emailAuthentication = mailQuery.getSingleResult();

        }catch(NoResultException e){
            throw new NotFoundException("token not found");
        }
        return emailAuthentication;
    }

    @Override
    public EmailAuthentication updateIsAuthAccount(EmailAuthentication emailAuthentication) {
        return entityManager.merge(emailAuthentication);
    }

    @Override
    public EmailAuthentication getEmailAuthenticationById(int id) {
        TypedQuery<EmailAuthentication> query = entityManager
                .createQuery("select e from EmailAuthentication e where e.id = :theId", EmailAuthentication.class);
        query.setParameter("theId", id);
        try {
            EmailAuthentication emailAuthentication = query.getSingleResult();
            if(emailAuthentication != null){
                return emailAuthentication;
            }
        }catch(NoResultException e){
          return null;
        }
        return null;
    }

    @Override
    public List<EmailAuthentication> getEmailAuthenticationByIdUser(int idUser) {
        TypedQuery<EmailAuthentication> query = entityManager.createQuery("select e from EmailAuthentication e  where e.adminUserAu.id = :idUser and CURRENT_DATE = function('DATE',e.tokenSentAt) ", EmailAuthentication.class);
        query.setParameter("idUser", idUser);

        return query.getResultList();
    }





}
