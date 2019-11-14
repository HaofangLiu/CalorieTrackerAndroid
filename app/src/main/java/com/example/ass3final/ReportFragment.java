package com.example.ass3final;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReportFragment extends Fragment {

    View vMain;
    final Calendar myCalendar = Calendar.getInstance();
    EditText PickDate,StartDate,EndDate;
    PieChart pieChart;
    Button showPie,showBar;
    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vMain = inflater.inflate(R.layout.fragment_report, container, false);
        PickDate = (EditText) vMain.findViewById(R.id.PickDate);
        pieChart = (PieChart) vMain.findViewById(R.id.pieChart);
        showPie = (Button) vMain.findViewById(R.id.btnShowPie);
        StartDate = (EditText) vMain.findViewById(R.id.StartDate);
        EndDate = (EditText) vMain.findViewById(R.id.EndDate);
        showBar = (Button) vMain.findViewById(R.id.showBar);
        barChart = (BarChart) vMain.findViewById(R.id.barchart);



        pieChart.setUsePercentValues(true);
        showPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!PickDate.getText().toString().equals("")) {
                    ReportAsyncTask generateReport = new ReportAsyncTask();
                    generateReport.execute();

                }
            }});

        showBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StartDate.getText().toString().equals("") & !EndDate.getText().toString().equals("")) {
                    BarAsyncTask barAsyncTask = new BarAsyncTask();
                    barAsyncTask.execute();

                }
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                PickDate.setText(sdf.format(myCalendar.getTime()));
            }

        };



//        StartDate = (EditText) vMain.findViewById(R.id.StartDate);
//        EndDate = (EditText) vMain.findViewById(R.id.EndDate);


        PickDate.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            if(!PickDate.getText().toString().equals("")) {
//                ReportAsyncTask generateReport = new ReportAsyncTask();
//                generateReport.execute();
//            }
        });

//        showPie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!PickDate.getText().toString().equals("")) {
//                    ReportAsyncTask generateReport = new ReportAsyncTask();
//                    generateReport.execute();
//
//            }
//        });

        //AppUser user =(AppUser) getActivity().getIntent().getSerializableExtra("user");
//        ReportAsyncTask generateReport = new ReportAsyncTask();
//        generateReport.execute();

        return vMain;
    }


    private class ReportAsyncTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            String DateSelected = PickDate.getText().toString();
            AppUser user =(AppUser) getActivity().getIntent().getSerializableExtra("user");
//            String reportObject = RestClient.findReportByFnameAndDate(user.getFirstname(),DateSelected);
//            String reportObj = "";
//            JSONArray jsonArr = null;
//            try {
//                jsonArr = new JSONArray(reportObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = jsonArr.getJSONObject(0);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            reportObj =  jsonObject.toString();
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//            Report report = gson.fromJson(reportObj, Report.class);
            String CalorieRemaining = RestClient.calculatRemain(user.getUserid(),DateSelected);

            return CalorieRemaining;
        }

        @Override
        protected void onPostExecute(String response) {
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//            Report report = gson.fromJson(response, Report.class);
            if(!response.equals("[]")){
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String totalcalorieconsumed = null;
            try {
                totalcalorieconsumed = jsonObject.getString("totalcalorieconsumed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String totalcalorieburned = null;
            try {
                totalcalorieburned = jsonObject.getString("totalcalorieburned");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String remainingcalorie = null;
            try {
                remainingcalorie = jsonObject.getString("remainingcalorie");
            } catch (JSONException e) {
                e.printStackTrace();
            }



            List<PieEntry> yvalues = new ArrayList<>();
            yvalues.add(new PieEntry(Integer.parseInt(totalcalorieconsumed), "totalcalorieconsumed"));
            yvalues.add(new PieEntry(Integer.parseInt(totalcalorieburned), "totalcalorieburned"));
            yvalues.add(new PieEntry(Integer.parseInt(remainingcalorie), "remainingcalorie"));

            PieDataSet dataSet = new PieDataSet(yvalues, "Report Results");

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());

            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.rgb(192,0,146));
            colors.add(Color.rgb(255,0,0));
            colors.add(Color.rgb(130,255,0));
            dataSet.setColors(colors);

            pieChart.setData(data);
            pieChart.invalidate();
            }
            else
            {
                pieChart.setData(null);
                pieChart.invalidate();
            }
        }
    }

    private class BarAsyncTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            String startDateS = StartDate.getText().toString();
            String EndDateS = EndDate.getText().toString();
            AppUser user =(AppUser) getActivity().getIntent().getSerializableExtra("user");
            String reportPeriod = RestClient.findReportBetweenTwoDates(user.getUserid(),startDateS,EndDateS);
            return reportPeriod;
        }


        @Override
        protected void onPostExecute(String result) {

            String totalcalorieconsumed = "";
            String totalcalorieburned= "";
            String totalstepstaken= "";

            if(!result.equals("[]")){
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                     totalcalorieconsumed = jsonObject.getString("totalcalorieconsumed");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                     totalcalorieburned = jsonObject.getString("totalcalorieburned");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                     totalstepstaken = jsonObject.getString("totalstepstaken");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<BarEntry> yvalues = new ArrayList<>();
                yvalues.add(new BarEntry(0f, (Float.parseFloat(totalcalorieconsumed))));
                yvalues.add(new BarEntry(1f, Float.parseFloat(totalcalorieburned)));
                yvalues.add(new BarEntry(2f, Float.parseFloat(totalstepstaken)));

                BarDataSet dataSet = new BarDataSet(yvalues, "Report Results");
                //LegendEntry entry = new LegendEntry();
                ArrayList<String> labels = new ArrayList<>();
                labels.add("totalcalorieconsumed");
                labels.add("totalcalorieburned");
                labels.add("totalstepstaken");
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

                BarData data = new BarData(dataSet);
                data.setValueFormatter(new PercentFormatter());

                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.rgb(141,23,146));
                colors.add(Color.rgb(255,134,32));
                colors.add(Color.rgb(83,255,13));
                dataSet.setColors(colors);
                barChart.setData(data);
                barChart.setFitBars(true);
                barChart.invalidate();

            }
            else{

                barChart.setData(null);
                barChart.invalidate();
            }

        }
    }


}
