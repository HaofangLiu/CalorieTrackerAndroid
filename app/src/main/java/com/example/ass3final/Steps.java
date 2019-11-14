package com.example.ass3final;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Steps {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "userid")
    public int userId;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "steps")
    public String steps;

    public Steps(int userId, String date, String steps) {
        this.userId = userId;
        this.date = date;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
