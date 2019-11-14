package com.example.ass3final;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText Username,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView)findViewById(R.id.textView);
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);
        String username = Username.getText().toString();
        String password = Password.getText().toString();
        Button Loginbtn = (Button) findViewById(R.id.btnLogin);
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAsyncTask loginAsy = new LoginAsyncTask();
                loginAsy.execute();
//                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
//                startActivity(intent);

            }
        });

        Button register =(Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }
    private class LoginAsyncTask extends AsyncTask<Void, Void, String>
    {

        String username = Username.getText().toString();
        String password = Password.getText().toString();

        String hexStringInput = "";
        @Override
        protected String doInBackground (Void...params){
            try{
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes("UTF-8"));
                StringBuffer hexString = new StringBuffer();

                for (int i = 0; i < hash.length; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if(hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                hexStringInput =  hexString.toString();
            } catch(Exception ex){
                throw new RuntimeException(ex);
            }
            //Gson gson = new Gson();
            String res = RestClient.findUser(username,hexStringInput);
            String userObj = "";
            //JsonObject jsonObject = new JsonObject("res");
            try {
                JSONArray jsonArr = new JSONArray(res);
                JSONObject jsonObject = jsonArr.getJSONObject(0).getJSONObject("userid");
                userObj =  jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return userObj;

        }
        @Override
        protected void onPostExecute (String result){
//            TextView resultTextView = (TextView) findViewById(R.id.loginResult);
//            resultTextView.setText(result);
            if (!result.equals("")) {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                AppUser user = gson.fromJson(result, AppUser.class);
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"wrong password or username", Toast.LENGTH_SHORT).show();
            }
        }
    }



}