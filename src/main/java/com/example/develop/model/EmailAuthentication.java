package com.example.develop.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="mail_authentication")
public class EmailAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="token_sent_at")
    private String tokenSentAt;
    @Column(name="token")
    private String token;
    @Column(name="is_auth")
    private int isAuth = 0;
    @Column(name="auth_completed_at")
    private String authCompleted_at;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private AdminUser adminUserAu;
    @Column(name = "email")
    private String email;
    @Column(name="activated")
    private int activated = 1;

    public EmailAuthentication() {
    }

    public EmailAuthentication(int id, String tokenSentAt, String token, int isAuth, String authCompleted_at, AdminUser adminUserAu, String email, int activated) {
        this.id = id;
        this.tokenSentAt = tokenSentAt;
        this.token = token;
        this.isAuth = isAuth;
        this.authCompleted_at = authCompleted_at;
        this.adminUserAu = adminUserAu;
        this.email = email;
        this.activated = activated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTokenSentAt() {
        return tokenSentAt;
    }

    public void setTokenSentAt(String tokenSentAt) {
        this.tokenSentAt = tokenSentAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(int isAuth) {
        this.isAuth = isAuth;
    }

    public String getAuthCompleted_at() {
        return authCompleted_at;
    }

    public void setAuthCompleted_at(String authCompleted_at) {
        this.authCompleted_at = authCompleted_at;
    }

    public AdminUser getAdminUserAu() {
        return adminUserAu;
    }

    public void setAdminUserAu(AdminUser adminUserAu) {
        this.adminUserAu = adminUserAu;
    }

    public int getActivated() {
        return activated;
    }

    public void setActivated(int activated) {
        this.activated = activated;
    }
}
