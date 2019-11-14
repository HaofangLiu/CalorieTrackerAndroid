package com.example.ass3final;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchParkAPI {

    //AIzaSyBtnPpKlM1jUu0NilIVeW0Z1b-tcnj-3uw

    private static final String Application_Keys = "AIzaSyBtnPpKlM1jUu0NilIVeW0Z1b-tcnj-3uw";

    public static String searchAllPark(double lat, double lng) {

        String urlAPI = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng + "&radius=5000&type=park&key=" + Application_Keys;
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try
        {
            url = new URL(urlAPI);
            Log.d("result:::","show:::" + url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch(
                Exception e)

        {
            e.printStackTrace();
        }finally

        {
            connection.disconnect();
        }
        Log.d("result:::","show:::" + textResult);
        return textResult;
    }

    public static ArrayList<String> findPark(String result){
        Log.d("result","show---" + result);
        ArrayList<String> park = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonhints = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonhints.length(); i++ ){
                JSONObject jsonObject1 = jsonhints.getJSONObject(i);
                String parkName  = jsonObject1.getString("name");
                park.add(parkName);//0
                String lat = jsonObject1.getJSONObject("geometry").getJSONObject("location").getString("lat");
                park.add(lat);//1
                String lng = jsonObject1.getJSONObject("geometry").getJSONObject("location").getString("lng");
                park.add(lng);//2
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return park;
    }


}
