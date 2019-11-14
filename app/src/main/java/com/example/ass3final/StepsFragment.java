package com.example.ass3final;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;


public class StepsFragment extends Fragment {

    View vRes;
    TextView steps,textViewAddDB,textViewReadDB,textViewDeleteDB,textViewUpdateDB;
    EditText stepsE;
    Button btnAddDB,btnAddNetbean,btnReadDB,btnDeleteDB,btnUpDateDB;
    StepsDatabase db = null;
    Calendar calendar = Calendar.getInstance();
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vRes = inflater.inflate(R.layout.fragment_steps, container, false);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                StepsDatabase.class, "StepsDatabase")
                .fallbackToDestructiveMigration()
                .build();
        steps = (TextView) vRes.findViewById(R.id.steps);
        stepsE = (EditText) vRes.findViewById(R.id.stepsE);
        btnAddDB = (Button) vRes.findViewById(R.id.btnAddDB);
        textViewAddDB = (TextView) vRes.findViewById(R.id.textViewAddDB);
        btnAddNetbean = (Button) vRes.findViewById(R.id.btnAddNetbean);
        btnReadDB = (Button) vRes.findViewById(R.id.btnReadDB);
        textViewReadDB = (TextView) vRes.findViewById(R.id.textViewReadDB) ;
        btnDeleteDB = (Button) vRes.findViewById(R.id.btnDeleteDB);
        textViewDeleteDB = (TextView) vRes.findViewById(R.id.textViewDeleteDB) ;
        btnUpDateDB = (Button) vRes.findViewById(R.id.btnUpDateDB);
        textViewUpdateDB = (TextView) vRes.findViewById(R.id.textViewUpdateDB) ;


        btnAddDB.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {

                if (isdigit(stepsE.getText().toString()) == false) {
                    stepsE.setError("Only number allow");
                }
                if (isdigit(stepsE.getText().toString()) == true){
                InsertDatabase addDatabase = new InsertDatabase();
                addDatabase.execute();
            }
            }
        });

        btnReadDB.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {




                ReadDatabase readDatabase = new ReadDatabase();
                readDatabase.execute();
            }
        });


        btnDeleteDB.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });



        btnUpDateDB.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                UpdateDatabase updateDatabase = new UpdateDatabase();
                updateDatabase.execute();
            }
        });

        btnAddNetbean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteToNetBean writeToNetBean = new WriteToNetBean();
                writeToNetBean.execute();

            }
        });

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        TaskTimer taskTimer = new TaskTimer();

        Date date = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(taskTimer,date,PERIOD_DAY);
        AlarmManager aManager=(AlarmManager)getActivity().getSystemService(Service.ALARM_SERVICE);
        Intent intent=new Intent();
        intent.setClass(getActivity(), TaskTimer.class);
        PendingIntent pi=PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return vRes;
    }

    private class WriteToNetBean extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            AppUser user = (AppUser) getActivity().getIntent().getSerializableExtra("user");
            int userID = user.getUserid();
            List<Steps> steps = db.stepsDao().findByuserId(userID);
            ArrayList<Integer> allSteps = new ArrayList<>();
            for(Steps stepS : steps){
                int stepsU = Integer.parseInt(stepS.getSteps());
                allSteps.add(stepsU);
            }
            int sum = 0;
            for(int i = 0; i < allSteps.size();i++){
                sum = sum + allSteps.get(i);
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date();
            String curTimeC = sdf.format(date1);

            String res = RestClient.findReportByFnameAndDate(user.getFirstname(),curTimeC);
            String reportObj = "";
            //JsonObject jsonObject = new JsonObject("res");
            try {
                JSONArray jsonArr = new JSONArray(res);
                JSONObject jsonObject = jsonArr.getJSONObject(0);
                reportObj =  jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Report report = gson.fromJson(reportObj, Report.class);
            report.setTotalsteptaken(String.valueOf(sum + Integer.parseInt(report.getTotalsteptaken())) );
            RestClient.setSteps(report);
            db.stepsDao().deleteAll();
            String redultSet = "Update successful";
            return redultSet;
        }


        @Override
        protected void onPostExecute(String details) {
            //textViewAddDB.setText("Added Record: " + details);
            Toast.makeText(getActivity().getApplicationContext(),details, Toast.LENGTH_SHORT).show();

        }
    }



    private class InsertDatabase extends AsyncTask<Void, Void, String>{
    @Override
    protected String doInBackground(Void... params) {
        AppUser user = (AppUser) getActivity().getIntent().getSerializableExtra("user");
        int userID = user.getUserid();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        String curTimeC = sdf.format(date1);
        if (!(stepsE.getText().toString().isEmpty())){
                Steps steps = new Steps(userID,curTimeC,stepsE.getText().toString());
                long id = db.stepsDao().insert(steps);
                return (id + " , UserId: " + userID + " , "+ " Current time: " + curTimeC + " \n Steps: " + stepsE.getText().toString());
            }
            else
                return "";
        }

    @Override
    protected void onPostExecute(String details) {
        textViewAddDB.setText("Added Record: " + details);
    }
}

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> steps = db.stepsDao().getAll();
            if (!(steps.isEmpty() || steps == null) ){
                String allSteps = "";
                for (Steps temp : steps) {
                    String stepsR = (temp.getId() + " " + temp.getUserId() + " " + temp.getDate() + " "+ temp.getSteps()+ " . ||\n " );
                    allSteps = allSteps + stepsR;
                }
                return allSteps;
            }
            else
                return "";
        }

        @Override
        protected void onPostExecute(String details) {
            textViewReadDB.setText("All data: " + details);
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

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.stepsDao().deleteAll();
            return null;
        }
        protected void onPostExecute(Void param) {
            textViewDeleteDB.setText("All data was deleted");
        }
    }

    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override protected String doInBackground(Void... params) {
            Steps steps=null;
            String[] details= stepsE.getText().toString().split(" ");
            if (details.length==4) {
                int id = Integer.parseInt(details[0]);
                steps = db.stepsDao().findByID(id);
                steps.setUserId(Integer.parseInt(details[1]));
                steps.setDate(details[2]);
                steps.setSteps(details[3]);
            }
            if (steps!=null) {
                db.stepsDao().updateUsers(steps);
                return (details[0] + " " + details[1] + " " + details[2] + " "+ details[3]);
            }
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
            textViewUpdateDB.setText("Updated details: "+ details);
        }
    }
}




