package com.example.develop.service;

import com.example.develop.model.EmailAuthentication;

import java.util.List;

public interface ConfirmationTokenService {
    void saveToken(EmailAuthentication emailAuthentication);
    EmailAuthentication findByConfirmationToken(String confirmationToken);
    EmailAuthentication updateIsAuthAccount(int id);
    List<EmailAuthentication> getEmailAuthenticationByIdUser(int idUser);

}
