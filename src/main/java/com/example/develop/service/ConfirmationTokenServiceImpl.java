package com.example.develop.service;

import com.example.develop.DAO.AdminUserDAO;
import com.example.develop.DAO.EmailDAO;
import com.example.develop.exception.NotFoundException;
import com.example.develop.model.AdminUser;
import com.example.develop.model.EmailAuthentication;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private EmailDAO emailDAO;
    private AdminUserDAO adminUserDAO;
    @Autowired
    public ConfirmationTokenServiceImpl(EmailDAO theEmailDAO, AdminUserDAO theAdminUserDAO) {
        this.emailDAO = theEmailDAO;
        this.adminUserDAO = theAdminUserDAO;
    }

    @Override
    public void saveToken(EmailAuthentication emailAuthentication) {
        final String regex = "^[a-z0-9]+(?!.*(?:\\+{2,}|\\-{2,}|\\.{2,}))(?:[\\.+\\-]{0,1}[a-z0-9])*@gmail\\.com$";
        AdminUser adminUser = adminUserDAO.getAdminUser(emailAuthentication.getAdminUserAu().getId());
        if(adminUser != null) {
            Pattern partern = Pattern.compile(regex);
            Matcher matcher = partern.matcher(emailAuthentication.getEmail());
            if(matcher.matches()){
                if (adminUserDAO.checkEmailExists(emailAuthentication.getEmail())!=null) {
                    throw new RuntimeException("Email already exists");
                } else {
                    emailAuthentication.setEmail(emailAuthentication.getEmail());
                    EmailAuthentication tokenEmail = new EmailAuthentication();
                    tokenEmail.setToken(emailAuthentication.getToken());
                    tokenEmail.setTokenSentAt(emailAuthentication.getTokenSentAt());
                    tokenEmail.setEmail(emailAuthentication.getEmail());
                    tokenEmail.setAdminUserAu(
                            emailAuthentication.getAdminUserAu()
                    );
                    emailDAO.saveToken(tokenEmail);
                }
            }
            else {
                throw new NotFoundException("email invalid");
            }
        }else{
            throw new NotFoundException("id user not found");
        }

    }

    @Override
    public EmailAuthentication findByConfirmationToken(String confirmationToken) {
        if (confirmationToken == null) {
            throw new IllegalArgumentException("confirmationToken is null");
        }else{
            EmailAuthentication emailAuthentication = emailDAO.findEmailByToken(confirmationToken);
          if (emailAuthentication == null) {
              throw new NotFoundException("token not found");
          }else{
              return emailAuthentication;
          }
        }
    }


    @Transactional
    @Override
    public EmailAuthentication updateIsAuthAccount(int id) {
            EmailAuthentication emailAuthentication = emailDAO.getEmailAuthenticationById(id);
            emailAuthentication.setIsAuth(1);
            emailAuthentication.setActivated(0);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formattedDate = formatter.format(date);
            emailAuthentication.setAuthCompleted_at(formattedDate);
        return emailDAO.updateIsAuthAccount(emailAuthentication);
    }

    @Override
    public List<EmailAuthentication> getEmailAuthenticationByIdUser(int idUser) {
        return emailDAO.getEmailAuthenticationByIdUser(idUser);
    }


}
