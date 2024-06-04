package com.example.develop.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="admin_users")
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="user_name")
    private String name;
    @Column(name="password")
    private String password;
    @Column(name="mail")
    private String mail;
    @Column(name="is_deleted")
    private int isDeleted = 0;
    @Column(name="deleted_at")
    private String deleted_at;
    @OneToMany(mappedBy = "adminUser", fetch = FetchType.LAZY , cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Payments> payments;
    @OneToMany(mappedBy = "adminUserAu",fetch = FetchType.LAZY , cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    List<EmailAuthentication> emailAuthentications;

    public AdminUser() {

    }
    public AdminUser( String name, String password, String mail, int isDeleted, String deleted_at) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.isDeleted = isDeleted;
        this.deleted_at = deleted_at;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }

    public List<EmailAuthentication> getEmailAuthentications() {
        return emailAuthentications;
    }

    public void setEmailAuthentications(List<EmailAuthentication> emailAuthentications) {
        this.emailAuthentications = emailAuthentications;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", is_deleted=" + isDeleted +
                ", deleted_at='" + deleted_at + '\'' +
                ", payments=" + payments +
                '}';
    }
}
