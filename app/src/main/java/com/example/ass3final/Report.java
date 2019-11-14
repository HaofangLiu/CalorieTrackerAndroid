package com.example.ass3final;

public class Report {

    private Integer reportid;
    private String date;
    private String totalcalorieconsumed;
    private String totalcalorieburned;
    private String totalsteptaken;
    private String setcaloriegoalthatday;
    private AppUser userid;

    public Report(Integer reportid, String date, String totalcalorieconsumed, String totalcalorieburned, String totalsteptaken, String setcaloriegoalthatday, AppUser userid) {
        this.reportid = reportid;
        this.date = date;
        this.totalcalorieconsumed = totalcalorieconsumed;
        this.totalcalorieburned = totalcalorieburned;
        this.totalsteptaken = totalsteptaken;
        this.setcaloriegoalthatday = setcaloriegoalthatday;
        this.userid = userid;
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalcalorieconsumed() {
        return totalcalorieconsumed;
    }

    public void setTotalcalorieconsumed(String totalcalorieconsumed) {
        this.totalcalorieconsumed = totalcalorieconsumed;
    }

    public String getTotalcalorieburned() {
        return totalcalorieburned;
    }

    public void setTotalcalorieburned(String totalcalorieburned) {
        this.totalcalorieburned = totalcalorieburned;
    }

    public String getTotalsteptaken() {
        return totalsteptaken;
    }

    public void setTotalsteptaken(String totalsteptaken) {
        this.totalsteptaken = totalsteptaken;
    }

    public String getSetcaloriegoalthatday() {
        return setcaloriegoalthatday;
    }

    public void setSetcaloriegoalthatday(String setcaloriegoalthatday) {
        this.setcaloriegoalthatday = setcaloriegoalthatday;
    }

    public AppUser getUserid() {
        return userid;
    }

    public void setUserid(AppUser userid) {
        this.userid = userid;
    }
}
