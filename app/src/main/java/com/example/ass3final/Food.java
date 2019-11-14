package com.example.ass3final;

public class Food {

    private Integer foodid;
    private String foodname;
    private String category;
    private String calorieamount;
    private String servingunit;
    private String servingamount;
    private String fat;

    public Food(Integer foodid, String foodname, String category, String calorieamount,
                String servingunit, String servingamount, String fat) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.category = category;
        this.calorieamount = calorieamount;
        this.servingunit = servingunit;
        this.servingamount = servingamount;
        this.fat = fat;
    }

    public Food() {
    }

    public Integer getFoodid() {
        return foodid;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCalorieamount() {
        return calorieamount;
    }

    public void setCalorieamount(String calorieamount) {
        this.calorieamount = calorieamount;
    }

    public String getServingunit() {
        return servingunit;
    }

    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }

    public String getServingamount() {
        return servingamount;
    }

    public void setServingamount(String servingamount) {
        this.servingamount = servingamount;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }
}
