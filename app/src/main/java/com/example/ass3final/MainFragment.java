package com.example.ass3final;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {
    TextView welcome,textViewCalorieGoal;
    View vMain;
    EditText editTextCalorieGoal;
    Button btnSetCalorieGoal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        textViewCalorieGoal = (TextView) vMain.findViewById(R.id.textViewCalorieGoal);
        editTextCalorieGoal = (EditText) vMain.findViewById(R.id.editTextSetCalorieGoal);
        btnSetCalorieGoal = (Button) vMain.findViewById(R.id.btnSetCalorieGoal);


        AppUser user = (AppUser) getActivity().getIntent().getSerializableExtra("user");
        welcome = vMain.findViewById(R.id.Welcome);
        String userNameD = user.getFirstname();
        welcome.setText("Hello, " + userNameD);
        FindReport findReport = new FindReport();
        findReport.execute(user);


        btnSetCalorieGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isdigit(editTextCalorieGoal.getText().toString()) == false) {
                    editTextCalorieGoal.setError("Only number allow");
                }


                if (isdigit(editTextCalorieGoal.getText().toString()) == true) {
                    CreateReportAsyn addNewReport = new CreateReportAsyn();
                    addNewReport.execute();
                }
            }
        });


        return vMain;
    }

    private class FindReport extends AsyncTask<AppUser, Void, Void> {

        @Override
        protected Void doInBackground(AppUser... params) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date();
            String curTimeC = sdf.format(date1);
            String res = RestClient.findReportByFnameAndDate(params[0].getFirstname(),curTimeC);
            if(!res.equals("[]")){
                String reportObj = "";
                JSONArray jsonArr = null;
                try {
                    jsonArr = new JSONArray(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArr.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reportObj =  jsonObject.toString();

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Report report = gson.fromJson(reportObj, Report.class);
                editTextCalorieGoal.setHint(report.getSetcaloriegoalthatday());
            }
            //return null;
            return null;
        }
    }

    public static boolean isdigit(String data)
    {


        for(int i=0;i<data.length();i++)
        {
            if (!Character.isDigit(data.charAt(i)))
                return false;

        }
        return true;
    }

    private class CreateReportAsyn extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {



            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date();
            String curTimeC = sdf.format(date1);

//            String res = RestClient.findReportByFnameAndDate(user.getFirstname(),curTimeC);
//            String reportObj = "";
//            //JsonObject jsonObject = new JsonObject("res");
//            try {
//                JSONArray jsonArr = new JSONArray(res);
//                JSONObject jsonObject = jsonArr.getJSONObject(0);
//                reportObj =  jsonObject.toString();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//            Report report = gson.fromJson(reportObj, Report.class);
//            report.setTotalsteptaken(String.valueOf(sum + Integer.parseInt(report.getTotalsteptaken())) );
//            RestClient.setSteps(report);

            AppUser user =(AppUser) getActivity().getIntent().getSerializableExtra("user");
            //user.setUserid(((AppUser) getActivity().getIntent().getSerializableExtra("user")).getUserid());
            String res = RestClient.findReportByFnameAndDate(user.getFirstname(),curTimeC);
            int reportId = Integer.parseInt(RestClient.countAllReport()) + 1;
            String result = "";
            if(res.equals("[]")) {
                Report report = new Report(reportId,curTimeC,"0","0","0",
                        editTextCalorieGoal.getText().toString(),user);
                        RestClient.createReport(report);
                result = "Goal has been set";
                return result;
            }else{

                String reportObj = "";
                JSONArray jsonArr = null;
                try {
                    jsonArr = new JSONArray(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArr.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reportObj =  jsonObject.toString();

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Report report = gson.fromJson(reportObj, Report.class);
                //editTextCalorieGoal.setHint(report.getSetcaloriegoalthatday());
                report.setSetcaloriegoalthatday(editTextCalorieGoal.getText().toString());
                RestClient.setSteps(report);
                result = "Goal is updated successful";
                return result;
            }


        }
        @Override
        protected void onPostExecute (String result) {
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
}
