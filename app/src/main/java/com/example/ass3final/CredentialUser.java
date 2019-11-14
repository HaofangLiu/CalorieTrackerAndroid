package com.example.ass3final;

import java.util.Date;

public class CredentialUser {

    private Integer credential;
    private String username;
    private String passwordhashing;
    private String signupdate;
    private AppUser userid;

    public CredentialUser(Integer credential, String username, String passwordhashing, String signupdate, AppUser userid) {
        this.credential = credential;
        this.username = username;
        this.passwordhashing = passwordhashing;
        this.signupdate = signupdate;
        this.userid = userid;
    }

//    public CredentialUser(Integer userid, String username, String passwordhashing, Date signupdate, AppUser appUser) {
//        this.userid = userid;
//        this.username = username;
//        this.passwordhashing = passwordhashing;
//        this.signupdate = signupdate;
//        this.appUser = appUser;
//    }


    public CredentialUser() {
    }

    public Integer getCredential() {
        return credential;
    }

    public void setCredential(Integer credential) {
        this.credential = credential;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhashing() {
        return passwordhashing;
    }

    public void setPasswordhashing(String passwordhashing) {
        this.passwordhashing = passwordhashing;
    }

    public String getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }

    public AppUser getUserid() {
        return userid;
    }

    public void setUserid(AppUser userid) {
        this.userid = userid;
    }
}
