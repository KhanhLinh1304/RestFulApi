package com.example.develop.service;

import com.example.develop.model.AdminUser;

public interface AdminUserService {
    AdminUser save(AdminUser adminUser) ;
    AdminUser deleteAdminUserUpdateStatus(int idUser);
    AdminUser login(String username, String password);
    AdminUser UpdateEmailAccount(int idUser, String email);
    AdminUser getAdminUser(int idUser);
    public String login(AdminUser adminUser);


 
}
