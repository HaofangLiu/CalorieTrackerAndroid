package com.example.ass3final;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodAPI {

    private static final String Application_ID = "3e5ac9f4";
    private static final String Application_Keys = "ef17025f4919d2348fff71a48b1e59c4";


    public static String searchFood(String foodname) {

        String urlAPI = "https://api.edamam.com/api/food-database/parser?ingr=" + foodname + "&app_id=" + Application_ID + "&app_key=" + Application_Keys;
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try
        {
            url = new URL(urlAPI);
            //Log.d("FoodAPI", "----" + url);
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
        return textResult;
    }

    public static ArrayList<String> findFoodInfo(String result){
        ArrayList<String> food = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonhints = jsonObject.getJSONArray("hints");
            JSONObject jsonObject1 = jsonhints.getJSONObject(0);
            JSONArray jsonmeasures = jsonObject1.getJSONArray("measures");
            JSONObject jsonObject2 = jsonmeasures.getJSONObject(0);
            String serveUnit =jsonObject2.getString("label");
            food.add(serveUnit);//0
            JSONArray jsonArray = jsonObject.getJSONArray("parsed");
            JSONObject jsonfood = jsonArray.getJSONObject(0).getJSONObject("food");
            String category = jsonfood.getString("category");
            food.add(category);//1
            JSONObject jsonnutrients =  jsonfood.getJSONObject("nutrients");
            String calamount = jsonnutrients.getString("ENERC_KCAL");
            String fat = jsonnutrients.getString("FAT");
            food.add(calamount);//2
            food.add(fat);//3

        }catch (Exception e){
            e.printStackTrace();

        }
        return food;
    }


}
