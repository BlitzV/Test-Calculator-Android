package com.test.chinchin.testcalculator.login;

public class LoginModel {

    private String Email;
    private String Password;
    private String TOKEN;

    public LoginModel(String email, String password, String TOKEN) {
        Email = email;
        Password = password;
        this.TOKEN = TOKEN;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }
}
