package com.example.develop.DAO;

import com.example.develop.model.AdminUser;

public interface AdminUserDAO {
     AdminUser save(AdminUser adminUser) ;
     AdminUser updateIsdeletedAdminUser(AdminUser adminUser);
     AdminUser getAdminUser(int idUser);
     AdminUser getAdminUserByName(String username);
     AdminUser checkEmailExists(String email);
     AdminUser UpdateEmailAccount(AdminUser adminUser);
     AdminUser updateIsActivatedAccountByIdUser(int idUser);



}
