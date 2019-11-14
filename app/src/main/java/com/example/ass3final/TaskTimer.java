package com.example.ass3final;

import android.arch.persistence.room.Room;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class TaskTimer extends TimerTask  {
    @Override
    public void run() {
//        View view = null;
//
//        db = Room.databaseBuilder(view.getActivity().getApplicationContext(),
//                StepsDatabase.class, "StepsDatabase")
//                .fallbackToDestructiveMigration()
//                .build();
//
//        AppUser user = (AppUser) getActivity().getIntent().getSerializableExtra("user");
//        int userID = user.getUserid();
//        List<Steps> steps = db.stepsDao().findByuserId(userID);
//        ArrayList<Integer> allSteps = new ArrayList<>();
//        for(Steps stepS : steps){
//            int stepsU = Integer.parseInt(stepS.getSteps());
//            allSteps.add(stepsU);
//        }
//        int sum = 0;
//        for(int i = 0; i < allSteps.size();i++){
//            sum = sum + allSteps.get(i);
//        }
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = new Date();
//        String curTimeC = sdf.format(date1);
//
//        String res = RestClient.findReportByFnameAndDate(user.getFirstname(),curTimeC);
//        String reportObj = "";
//        //JsonObject jsonObject = new JsonObject("res");
//        try {
//            JSONArray jsonArr = new JSONArray(res);
//            JSONObject jsonObject = jsonArr.getJSONObject(0);
//            reportObj =  jsonObject.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//        Report report = gson.fromJson(reportObj, Report.class);
//        report.setTotalsteptaken(String.valueOf(sum + Integer.parseInt(report.getTotalsteptaken())) );
//        RestClient.setSteps(report);
//        db.stepsDao().deleteAll();

    }
}
