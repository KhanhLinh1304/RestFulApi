package com.example.develop.security;

import com.example.develop.DAO.AdminUserDAO;
import com.example.develop.exception.NotFoundException;
import com.example.develop.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private AdminUserDAO adminUserDAO;
    @Autowired
    public CustomUserDetailService(AdminUserDAO adminUserDAO) {
        this.adminUserDAO = adminUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser user =  adminUserDAO.getAdminUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found with user name");
        }
        return User.withUsername(user.getName())
                .password(user.getPassword()).build();
    }
}
