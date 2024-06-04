package com.example.develop.DAO;

import com.example.develop.model.AdminUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AdminUserDAOImpl implements AdminUserDAO{
    private EntityManager entityManager;
    @Override
    public AdminUser getAdminUserByName(String username) {
        TypedQuery<AdminUser> theQuery = entityManager
                .createQuery("select a from AdminUser a where a.name=:theData", AdminUser.class);

        theQuery.setParameter("theData",username);
        try {
            AdminUser admin = theQuery.getSingleResult();
            return admin;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public AdminUser checkEmailExists(String email) {
        TypedQuery<AdminUser> adminUser = entityManager
                .createQuery("select a from AdminUser a where a.mail =:theData", AdminUser.class);

        adminUser.setParameter("theData",email);

        try {
            AdminUser admin = adminUser.getSingleResult();
            return admin;

        }catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public AdminUser UpdateEmailAccount(AdminUser adminUser) {
        return entityManager.merge(adminUser);
    }

    @Override
    public AdminUser updateIsActivatedAccountByIdUser(int idUser) {
        return null;
    }


    @Autowired
    public AdminUserDAOImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }
    @Override
    @Transactional
    public AdminUser save(AdminUser adminUser) {
         entityManager.persist(adminUser);
         return adminUser;
    }


    @Override
    @Transactional
    public AdminUser updateIsdeletedAdminUser(AdminUser adminUser) {
       return entityManager.merge(adminUser);
    }


    @Override
    public AdminUser getAdminUser(int idUser) {
        TypedQuery<AdminUser> theQuery = entityManager.createQuery("select a from AdminUser a where a.id=:theData", AdminUser.class);
         theQuery.setParameter("theData",idUser);
            AdminUser adminUser;
            try {
                adminUser = theQuery.getSingleResult();
                return adminUser;
            }catch(NoResultException e) {
                return null;
            }
    }



}
