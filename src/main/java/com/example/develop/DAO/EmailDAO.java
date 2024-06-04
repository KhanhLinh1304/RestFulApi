package com.example.develop.DAO;

import com.example.develop.model.EmailAuthentication;

import java.util.List;

public interface EmailDAO {
   void saveToken(EmailAuthentication emailAuthentication);
   EmailAuthentication findEmailByToken(String token);
   EmailAuthentication updateIsAuthAccount(EmailAuthentication emailAuthentication);
   EmailAuthentication getEmailAuthenticationById(int id);
   List<EmailAuthentication> getEmailAuthenticationByIdUser(int idUser);



}
