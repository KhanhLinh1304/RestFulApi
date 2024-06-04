package com.example.develop.controller;

import com.example.develop.DTO.MailDTO;
import com.example.develop.DTO.ResponseToken;
import com.example.develop.exception.NotFoundException;
import com.example.develop.model.AdminUser;
import com.example.develop.model.EmailAuthentication;
import com.example.develop.repository.EmaiAccountRepository;
import com.example.develop.service.AdminUserService;
import com.example.develop.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user/")
public class RestAdminUser {
    private AdminUserService adminUserService;
    private ConfirmationTokenService confirmationTokenService;
    private EmaiAccountRepository emaiAccountRepository;
    @Autowired
    public RestAdminUser(AdminUserService theAdminUserService, ConfirmationTokenService theConfirmationTokenService,EmaiAccountRepository theEmaiAccountRepository) {
        this.adminUserService = theAdminUserService;
        this.confirmationTokenService = theConfirmationTokenService;
        this.emaiAccountRepository = theEmaiAccountRepository;
    }

@GetMapping("/welcome")
public String getWelcome()
{
    return "welcome to you";
}
    @PostMapping("create")
    public AdminUser save(@RequestBody AdminUser adminUser) {
        AdminUser admin =  adminUserService.save(adminUser);
        if (admin != null){
           return admin;
        }
      throw new NotFoundException("user existed");
    }


    @PostMapping("confirm-mail")
    public MailDTO confirmAccount(@RequestBody AdminUser adminUser){
        AdminUser theadminUser = adminUserService.getAdminUser(adminUser.getId());
        if(theadminUser == null){
            throw new NotFoundException("user does not exist");
        }else{
            if(theadminUser.getIsDeleted() == 1){
                throw new NotFoundException("user is deleted");
            }
            List<EmailAuthentication> emailAuthentications = confirmationTokenService.getEmailAuthenticationByIdUser(adminUser.getId());
            if(emailAuthentications.size() == 5 ){
               throw new NotFoundException("user chỉ đc verify 5 lần trong 1 ngày");
            }else{
                Date createdAt = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String tokenSentAt = formatter.format(createdAt);
                String token = UUID.randomUUID().toString();
                EmailAuthentication emailAuthentication = new EmailAuthentication();
                emailAuthentication.setToken(token);
                emailAuthentication.setEmail(adminUser.getMail());
                emailAuthentication.setTokenSentAt(tokenSentAt);
                emailAuthentication.setAdminUserAu(theadminUser);
                confirmationTokenService.saveToken(emailAuthentication);
                List<EmailAuthentication> emails = confirmationTokenService.getEmailAuthenticationByIdUser(adminUser.getId());
                if(emails != null ){
                    emaiAccountRepository.updateIsActivatedEmailAuthenticationByUserId(adminUser.getId(),0);
                    for(EmailAuthentication email : emails){
                        if(email.getToken().equals(token)){
                            //email.setActivated(1);
                            emaiAccountRepository.updateIsActivatedEmailAuthenticationByUserIdToken(adminUser.getId(),token);
                        }
                    }

                }
                MailDTO mailDTO = new MailDTO();
                mailDTO.setUsername(theadminUser.getName());
                mailDTO.setUrl("http://localhost:8080/api/user/confirm-account?token="+token);
                return mailDTO;
            }
        }
    }

    @GetMapping("confirm-account")
    public String ConfirmAccount(@RequestParam String token) {
       EmailAuthentication emailAuthentication = confirmationTokenService.findByConfirmationToken(token);
        if(emailAuthentication != null) {

                if (emailAuthentication.getActivated() == 1) {
                    int id = emailAuthentication.getId();
                    if (emailAuthentication.getIsAuth() == 0) {
                        EmailAuthentication emailAuthentication1 = confirmationTokenService.updateIsAuthAccount(id);
                        if (emailAuthentication1.getIsAuth() == 1) {
                            adminUserService.updateEmailAccount(emailAuthentication1.getAdminUserAu().getId(), emailAuthentication1.getEmail());
                        }
                        return "confirm account success";
                    } else {
                        return "account confirmed";

                    }
                } else {
                    throw new NotFoundException("token expired");
                }
            }

        return "confirm account failed";
    }
    @PatchMapping("delete/{idUser}")
    public String delete(@PathVariable int idUser){
        int id = 8;
        if (id == idUser) {
            AdminUser adminUser = adminUserService.deleteAdminUserUpdateStatus(idUser);
            if (adminUser == null) {
                throw new NotFoundException("user id do not exist");
            }
            return "id deleted: " + adminUser.getId() + " is_deleted : " +adminUser.getIsDeleted();
        }else{
            throw new NotFoundException("you do not have permission");
        }
    }

/*    @PostMapping("login")
    public String login(@RequestBody AdminUser adminUser){

        AdminUser admin =  adminUserService.login(adminUser.getName(),adminUser.getPassword());
        if(admin != null){
            if(admin.getIsDeleted() == 0){
                return "logged-in user id : " + admin.getId();
            }else{
                throw new NotFoundException("user id deleted");
            }
        }
        throw new NotFoundException("user name or password incorrect");
    }*/
@PostMapping("login")
public ResponseToken login(@RequestBody AdminUser adminUser){

   String token = adminUserService.login(adminUser);
    ResponseToken responseToken = new ResponseToken();
    responseToken.setToken(token);
    return responseToken;
}

}
