package com.example.ass3final;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";


    EditText UsernameR,editTextFirstName,editTextPassword,editTextSurName,editTextEmail,editTextHeight,editTextWeight,editTextAddress,editTextPostcode,editTextStepPerMile;
    TextView editTextDOB,textViewSpinner;
    RadioGroup radioGender;
    Spinner levelOfActivity;
    Button btnRegister;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String gender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsernameR = (EditText) findViewById(R.id.UsernameR);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextSurName = (EditText) findViewById(R.id.editTextSurName);
        editTextDOB = (TextView) findViewById(R.id.editTextDOB);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextHeight = (EditText) findViewById(R.id.editTextHeight);
        editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        radioGender = (RadioGroup) findViewById(R.id.radioGender);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPostcode = (EditText) findViewById(R.id.editTextPostcode);
        textViewSpinner = (TextView) findViewById(R.id.textViewSpinner) ;
        levelOfActivity = (Spinner) findViewById(R.id.levelOfActivity);
        editTextStepPerMile = (EditText) findViewById(R.id.editTextStepPerMile);



        if(UsernameR.getText().toString().trim().equalsIgnoreCase("")) {
            UsernameR.setError("User name cannot be null");
        }
        if(editTextPassword.getText().toString().trim().equalsIgnoreCase("")) {
            editTextPassword.setError("Password cannot be null");
        }
        if(editTextFirstName.getText().toString().trim().equalsIgnoreCase("")) {
            editTextFirstName.setError("First name cannot be null");
        }
        if(editTextSurName.getText().toString().trim().equalsIgnoreCase("")) {
            editTextSurName.setError("Last name cannot be null");
        }

        if(editTextEmail.getText().toString().trim().equalsIgnoreCase("")) {
            editTextEmail.setError("Email cannot be null");
        }



        if(editTextHeight.getText().toString().trim().equalsIgnoreCase("")) {
            editTextHeight.setError("Height cannot be null");
        }


        if(editTextWeight.getText().toString().trim().equalsIgnoreCase("")) {
            editTextWeight.setError("Weight cannot be null");
        }



        if(editTextPostcode.getText().toString().trim().equalsIgnoreCase("")) {
            editTextPostcode.setError("POSTCODE cannot be null");
        }



        if(editTextStepPerMile.getText().toString().trim().equalsIgnoreCase("")) {
            editTextStepPerMile.setError("Steps cannot be null");
        }




        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: yyy/mm/dd/: " + month + "-" + day + "-" + year);

            String date = year + "-" + month + "-" + day;
            editTextDOB.setText(date);
        };

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelOfActivity.setAdapter(dataAdapter);


//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                try {
//                    updateLabel();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            private void updateLabel() throws ParseException {
//                String myFormat = "yyyy-MM-dd";
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
//                editTextDOB.setText(sdf.format(myCalendar.getTime()));
//                dobU = (Date) sdf.parse(String.valueOf(editTextDOB.getText()));
//            }
//
//        };
//
//        editTextDOB.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(RegisterActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });


        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }

            private void selectRadioBtn() {
                RadioButton rb = (RadioButton)RegisterActivity.this.findViewById(radioGender.getCheckedRadioButtonId());
                gender = rb.getText().toString();
            }
        });



        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (isdigit(editTextStepPerMile.getText().toString()) == false) {
                    editTextStepPerMile.setError("Only number allow");
                }

                if (isdigit(editTextPostcode.getText().toString()) == false) {
                    editTextPostcode.setError("Only number allow");
                }

                //isEmailValid
                if (isEmailValid(editTextEmail.getText().toString().trim()) == false) {
                    editTextEmail.setError("Format of email is not correct");
                }

                //isdigit
                if (isdigit(editTextHeight.getText().toString()) == false) {
                    editTextHeight.setError("Only number allow");
                }


                if (isdigit(editTextWeight.getText().toString()) == false) {
                    editTextWeight.setError("Only number allow");
                }


//
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//                try {
//                    Date dob = (Date) sdf.parse(String.valueOf(editTextDOB.getText()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                if (isdigit(editTextStepPerMile.getText().toString()) == true && isdigit(editTextPostcode.getText().toString()) == true &&
                        isEmailValid(editTextEmail.getText().toString().trim()) == true &&
                        isdigit(editTextHeight.getText().toString()) == true &&
                        isdigit(editTextWeight.getText().toString()) == true
                ) {
                    PostAsyncTask postAsyncTask = new PostAsyncTask();
                    try {
                        postAsyncTask.execute(

                                //String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(stringText);

                                UsernameR.getText().toString(),//0
                                getStringSHA256(editTextPassword.getText().toString()),//1
                                editTextFirstName.getText().toString(),//2
                                editTextSurName.getText().toString(),//3
                                editTextDOB.getText().toString(),//4
                                editTextEmail.getText().toString(),//5
                                editTextHeight.getText().toString(),//6
                                editTextWeight.getText().toString(),//7
                                gender,//8
                                editTextAddress.getText().toString(),//9
                                editTextPostcode.getText().toString(),//10
                                levelOfActivity.getSelectedItem().toString(),//11
                                editTextStepPerMile.getText().toString()//12
                        );
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

//                Intent LoginIntent = new Intent(RegisterActivity.this,MainActivity.class);
//                startActivity(LoginIntent);
                }
            }
        });

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

    public static boolean ischar(String data)
    {


        for(int i=0;i<data.length();i++)
        {
            if (!Character.isDigit(data.charAt(i)))
                return true;

        }
        return false;
    }

//    public void validation(){
//        if(UsernameR.getText().toString().trim().equalsIgnoreCase("")) {
//            UsernameR.setError("User name cannot be null");
//        }
//        if(editTextPassword.getText().toString().trim().equalsIgnoreCase("")) {
//            editTextPassword.setError("Password cannot be null");
//        }
//        if(editTextFirstName.getText().toString().trim().equalsIgnoreCase("")) {
//            editTextFirstName.setError("First name cannot be null");
//        }
//        if(editTextSurName.getText().toString().trim().equalsIgnoreCase("")) {
//            editTextSurName.setError("Last name cannot be null");
//        }
//
//        //isEmailValid
//        if(isEmailValid(editTextEmail.getText().toString().trim()) == false) {
//            editTextEmail.setError("Format of email is not correct");
//        }
//
//        //isdigit
//        if(isdigit(editTextHeight.getText().toString()) == false) {
//            editTextHeight.setError("Only number allow");
//        }
//
//        if(editTextHeight.getText().toString().trim().equalsIgnoreCase("")) {
//            editTextHeight.setError("Height cannot be null");
//        }
//
//        if(isdigit(editTextWeight.getText().toString()) == false) {
//            editTextWeight.setError("Only number allow");
//        }
//
//        if(editTextWeight.getText().toString().trim().equalsIgnoreCase("")) {
//            editTextWeight.setError("Weight cannot be null");
//        }
//
//        if(isdigit(editTextPostcode.getText().toString()) == false) {
//            editTextPostcode.setError("Only number allow");
//        }
//
//        if(editTextPostcode.getText().toString().trim().equalsIgnoreCase("")) {
//            editTextPostcode.setError("POSTCODE cannot be null");
//        }
//
//        if(isdigit(editTextStepPerMile.getText().toString()) == false) {
//            editTextStepPerMile.setError("Only number allow");
//        }
//
//        if(editTextStepPerMile.getText().toString().trim().equalsIgnoreCase("")) {
//            editTextStepPerMile.setError("Steps cannot be null");
//        }
//    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    public static String getStringSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;



    }

    private class PostAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            int allUser = Integer.parseInt(RestClient.countAllUser());
            int userID = allUser + 1;

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date();
            String curTime = sdf.format(date1);
            //validation();
            AppUser appuser1 = null;

            appuser1 = new AppUser(userID,params[2],params[3],params[4],
                    params[5],params[6],
                    params[7],params[8],params[9],
                    params[10], params[11],params[12]);
            CredentialUser userNewCre = new CredentialUser(userID,params[0],params[1],curTime,appuser1);


            Boolean repeatEmail = false;
            ArrayList<AppUser> allUsers = new ArrayList<>();
            String allUserString = RestClient.allUsers();
            JSONArray jsonArray1 = null;
            try {
                jsonArray1 = new JSONArray(allUserString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i = 0;i< jsonArray1.length();i++){
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 = jsonArray1.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String userObj = jsonObject1.toString();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                AppUser userOBJE = gson.fromJson(userObj, AppUser.class);
                allUsers.add(userOBJE);
            }
            for(AppUser user2 : allUsers){
                if(user2.getEmail().equals(appuser1.getEmail()))
                    repeatEmail = true;
            }

            Boolean repeatUserName = false;
            ArrayList<CredentialUser> allCreO = new ArrayList<>();
            String allCreObj = RestClient.allCreden();
            JSONArray jsonArrayCre = null;
            try {
                jsonArrayCre = new JSONArray(allCreObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i = 0;i< jsonArrayCre.length();i++){
                JSONObject jsonObjectCre = null;
                try {
                    jsonObjectCre = jsonArrayCre.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String credentialObj = jsonObjectCre.toString();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                CredentialUser credentialUser = gson.fromJson(credentialObj, CredentialUser.class);
                allCreO.add(credentialUser);
            }
            for(CredentialUser credentialUser1 : allCreO){
                if(credentialUser1.getUsername().equals(userNewCre.getUsername()))
                    repeatUserName = true;
            }




            if(repeatEmail == false && repeatUserName == false) {

                RestClient.createUser(appuser1);
                RestClient.createCreUser(userNewCre);

                return "Register successful";
            }
            else
            {
                return "Register failed. Duplicate username or email";
            }

        }
        @Override
        protected void onPostExecute(String response) {


            Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}

