package com.example.develop.DTO;

public class MailDTO {

    private String username;

    private String url;
    public MailDTO() {}
    public MailDTO(String username,  String url) {

        this.username = username;

        this.url = url;

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
