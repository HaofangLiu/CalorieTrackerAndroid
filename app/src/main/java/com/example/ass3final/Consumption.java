package com.example.ass3final;

public class Consumption {

    private Integer consumptionid;
    private String date;
    private String quantity;
    private Food foodid;
    private AppUser userid;

    public Consumption(Integer consumptionid, String date, String quantity, Food foodid, AppUser userid) {
        this.consumptionid = consumptionid;
        this.date = date;
        this.quantity = quantity;
        this.foodid = foodid;
        this.userid = userid;
    }

    public Integer getConsumptionid() {
        return consumptionid;
    }

    public void setConsumptionid(Integer consumptionid) {
        this.consumptionid = consumptionid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
    }

    public AppUser getUserid() {
        return userid;
    }

    public void setUserid(AppUser userid) {
        this.userid = userid;
    }
}
