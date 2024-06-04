package com.example.develop.service;

import com.example.develop.DAO.AdminUserDAO;
import com.example.develop.exception.NotFoundException;
import com.example.develop.jwt.JwtAuthenticationProvider;
import com.example.develop.model.AdminUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserDAO adminUserDAO;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminUserServiceImpl(AdminUserDAO adminUserDAO, AuthenticationManager authenticationManager, JwtAuthenticationProvider authenticationProvider, PasswordEncoder passwordEncoder) {
        this.adminUserDAO = adminUserDAO;
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public AdminUser save(AdminUser adminUser) {
        AdminUser adminUserSaved = null;
        if(adminUserDAO.getAdminUserByName(adminUser.getName()) == null) {
            AdminUser newAdminUser = new AdminUser();
            String pasE = passwordEncoder.encode(adminUser.getPassword());
            if (adminUser.getName().length() < 8) {
                newAdminUser.setName(adminUser.getName());
                newAdminUser.setPassword(pasE);
                if (adminUserDAO.checkEmailExists(adminUser.getMail())!=null) {
                    throw new RuntimeException("Email already exists");
                } else {
                    adminUserSaved = adminUserDAO.save(newAdminUser);
                }
            } else {
                throw new NotFoundException("username is not longer than 8 character");
            }
        }

        return adminUserSaved;
    }

    @Override
    public AdminUser deleteAdminUserUpdateStatus(int idUser) {
        AdminUser adminUser = adminUserDAO.getAdminUser(idUser);
        if(adminUser != null){
            Date date = new Date();
            SimpleDateFormat spDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatDate = spDate.format(date);
            adminUser.setIsDeleted(1);
            adminUser.setDeleted_at(formatDate);
            return adminUserDAO.updateIsdeletedAdminUser(adminUser);
        }
        return adminUser;
    }

    @Override
    public AdminUser login(String username, String password) {
        AdminUser admin = adminUserDAO.getAdminUserByName(username);
        if (admin == null) {
            throw new NotFoundException("user name or password incorrect");
        } else {
            boolean check = (passwordEncoder.matches(password, admin.getPassword()));
            return check ? adminUserDAO.getAdminUserByName(username) : null;
        }
    }


    @Override
    public String login(AdminUser adminUser) {
        AdminUser admin = adminUserDAO.getAdminUserByName(adminUser.getName());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        adminUser.getName(),
                        adminUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authenticationProvider.generateToken(authentication);
    }

    @Transactional
    @Override
    public AdminUser UpdateEmailAccount(int idUser, String email) {
        AdminUser adminUser = adminUserDAO.getAdminUser(idUser);
        if (adminUser != null) {
            adminUser.setMail(email);
            adminUserDAO.UpdateEmailAccount(adminUser);
        }
        return adminUser;
    }

    @Override
    public AdminUser getAdminUser(int idUser) {
        try {
            return adminUserDAO.getAdminUser(idUser);
        } catch (Exception e) {
            return null;
        }
    }
}
