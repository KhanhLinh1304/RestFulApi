package com.example.develop.repository;

import com.example.develop.model.EmailAuthentication;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmaiAccountRepository extends JpaRepository<EmailAuthentication, Integer> {
    @Transactional
    @Modifying
    @Query("update EmailAuthentication  e set e.activated = :activate where e.adminUserAu.id = :idUser")
    void updateIsActivatedEmailAuthenticationByUserId( @Param("idUser") Integer idUser, @Param("activate") Integer activate);

    @Transactional
    @Modifying
    @Query("update EmailAuthentication e set e.activated = 1 where e.adminUserAu.id = :idU and e.token = :token")
    void updateIsActivatedEmailAuthenticationByUserIdToken(@Param("idU") Integer idU, @Param("token") String token);
}
