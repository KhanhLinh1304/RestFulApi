package com.example.develop.DTO;

public class ResponseToken {
    private String token;
    private String tokenType="Bearer";

    public ResponseToken(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public ResponseToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
