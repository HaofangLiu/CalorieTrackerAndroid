package com.example.ass3final;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class AppUser implements Serializable {

    private Integer userid;
    private String firstname;
    private String surname;
    private String dob;
    private String email;
    private String height;
    private String weight;
    private String gender;
    private String address;
    private String postcode;
    private String levelofactivityonetofive;
    private String steppermile;

    public AppUser(Integer userid, String firstname, String surname, String dob, String email, String height, String weight, String gender, String address, String postcode, String levelofactivityonetofive, String steppermile) {
        this.userid = userid;
        this.firstname = firstname;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelofactivityonetofive = levelofactivityonetofive;
        this.steppermile = steppermile;
    }


    //    public AppUser(Integer userid, String firstname, String surname, Date dob, String email, int height, BigDecimal weight, String gender, String address, short postcode, short levelofactivityonetofive, int steppermile, String dobStr) {
//        this.userid = userid;
//        this.firstname = firstname;
//        this.surname = surname;
//        this.dob = dob;
//        this.email = email;
//        this.height = height;
//        this.weight = weight;
//        this.gender = gender;
//        this.address = address;
//        this.postcode = postcode;
//        this.levelofactivityonetofive = levelofactivityonetofive;
//        this.steppermile = steppermile;
//        this.dobStr = dobStr;
//    }

    public AppUser() {
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLevelofactivityonetofive() {
        return levelofactivityonetofive;
    }

    public void setLevelofactivityonetofive(String levelofactivityonetofive) {
        this.levelofactivityonetofive = levelofactivityonetofive;
    }

    public String getSteppermile() {
        return steppermile;
    }

    public void setSteppermile(String steppermile) {
        this.steppermile = steppermile;
    }

}

