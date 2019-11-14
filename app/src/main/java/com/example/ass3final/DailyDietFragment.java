package com.example.ass3final;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;


public class DailyDietFragment extends Fragment {

    View vRes;
    Spinner spinnerFoodCate,spinnerFoodItem;
    EditText editTextAddFoodItem;
    Button btnAddConsumption,btnAddFood,btnFindFood;
    TextView textViewSearch;
    ImageView imageViewSearch;
    List<Food> food;
    ArrayList<String> foodCate;// = new ArrayList<>();
    ArrayList<String> foodName;

    public DailyDietFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vRes = inflater.inflate(R.layout.fragment_daily_diet, container, false);

        AppUser user = (AppUser) getActivity().getIntent().getSerializableExtra("user");
        spinnerFoodCate = (Spinner) vRes.findViewById(R.id.spinnerFoodCate);
        spinnerFoodItem = (Spinner) vRes.findViewById(R.id.spinnerFoodItem);
        editTextAddFoodItem = (EditText)vRes.findViewById(R.id.editTextAddFoodItem);
        btnAddFood = (Button)vRes.findViewById(R.id.btnAddFood);
        textViewSearch = (TextView)vRes.findViewById(R.id.textViewSearch);
        imageViewSearch = (ImageView)vRes.findViewById(R.id.imageViewSearch);
        btnFindFood = (Button)vRes.findViewById(R.id.btnFindFood) ;
        foodCate = new ArrayList<>();
        foodName = new ArrayList<>();
        food = new ArrayList<>();
        //ArrayAdapter<String> foodItem = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodName);
        FoodAsyncTask foodAsy = new FoodAsyncTask();
        foodAsy.execute();

//        FoodItemAsync foodItemAsync = new FoodItemAsync();
//        foodItemAsync.execute();

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodCate);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerFoodCate.setAdapter(dataAdapter);
//        spinnerFoodCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String foodCateSelected = parent.getItemAtPosition(position).toString();
//                Log.d("kkkk",""+position);
//                //System.out.println(foodCateSelected);
//                for(Food food2 : food){
//                    if(food2.getCategory().equals(foodCateSelected)){
//                        foodName.add(food2.getFoodname());
//                    }
//                }
//                ArrayAdapter<String> foodItem = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodName);
//                foodItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerFoodItem.setAdapter(foodItem);
//                foodItem.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//
//
//        });

        btnAddConsumption = (Button)vRes.findViewById(R.id.btnAddConsumption);
        btnAddConsumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddConsumpAsyn addConsumpAsyn = new AddConsumpAsyn();
                addConsumpAsyn.execute(spinnerFoodItem.getSelectedItem().toString());
            }
        });


        btnFindFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editTextAddFoodItem.getText().toString();
                SearchAsyncTask searchAsyncTask=new SearchAsyncTask();
                searchAsyncTask.execute(keyword);
            }
        });

//        spinnerFoodItem.setOnItemSelectedListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        spinnerFoodItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String keyword = spinnerFoodItem.getSelectedItem().toString();
                SearchAsyncTask searchAsyncTask=new SearchAsyncTask();
                searchAsyncTask.execute(keyword);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editTextAddFoodItem.getText().toString();
                AddfooAsyn addfooAsyn = new AddfooAsyn();
                addfooAsyn.execute(keyword);

                FoodAsyncTask foodAsy = new FoodAsyncTask();
                foodAsy.execute();

            }
        });




        return vRes;


    }

    private class AddfooAsyn extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            int foodId = Integer.parseInt(RestClient.countAllFood()) + 10;

            String foodFind = FoodAPI.searchFood(params[0]);
            ArrayList<String> foodInformation = new ArrayList<>();
            foodInformation = FoodAPI.findFoodInfo(foodFind);
            Food foodOBJ = new Food(foodId,params[0],foodInformation.get(1),foodInformation.get(2),foodInformation.get(0),"1",foodInformation.get(3));
            RestClient.createFood(foodOBJ);

//            String foodCateSelected = spinnerFoodCate.getSelectedItem().toString();
//            foodName = new ArrayList<>();
//            for(Food food2 : food){
//                if(food2.getCategory().equals(foodCateSelected)){
//                    foodName.add(food2.getFoodname());
//                }
//            }
//            ArrayAdapter<String> foodItem = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodName);
//            foodItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerFoodItem.setAdapter(foodItem);
//            foodItem.notifyDataSetChanged();
            String result = "Add food success";
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity().getApplicationContext(),s, Toast.LENGTH_SHORT).show();
        }
    }


    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            textViewSearch.setText(SearchGoogleAPI.getSnippet(result));
            Glide.with(getActivity()).load(SearchGoogleAPI.getPicUrl(result)).into(imageViewSearch);
        }
    }

    //private class FoodAsyncTask extends AsyncTask<Void,Void,String>
    private class FoodAsyncTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            String foodAll = RestClient.findFood();
            return foodAll;
            }

            @Override
            protected void onPostExecute(String result){
                food = new ArrayList<>();
            JSONArray jsonArr = null;
            try {
                jsonArr = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i = 0;i < jsonArr.length();i++) {
                Gson gson = new Gson();
               JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArr.getJSONObject(i);
               } catch (JSONException e) {
                    e.printStackTrace();
               }
                String foodObj = jsonObject.toString();
                Food food1 = (Food) gson.fromJson(foodObj,Food.class);
                food.add(food1);

                for (int j = 0;j<food.size();j++) {
//                    for (int h = 0; h < foodCate.size(); h++) {
//                        if (!food.get(j).getCategory().equals(foodCate.get(h))) {
//                            foodCate.add(food.get(j).getCategory());
                    if(!foodCate.contains(food.get(j).getCategory())){
                        foodCate.add(food.get(j).getCategory());
                        }
                    }
                }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodCate);
                //dataAdapter.notifyDataSetChanged();
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFoodCate.setAdapter(dataAdapter);

        spinnerFoodCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String foodCateSelected = parent.getItemAtPosition(position).toString();
                foodName = new ArrayList<>();
                for(Food food2 : food){
                    if(food2.getCategory().equals(foodCateSelected)){
                        foodName.add(food2.getFoodname());
                    }
                }
                ArrayAdapter<String> foodItem = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodName);
                foodItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFoodItem.setAdapter(foodItem);
                foodItem.notifyDataSetChanged();
           }

          @Override
            public void onNothingSelected(AdapterView<?> parent) {

         }

       });

        }


    }


    private class AddConsumpAsyn extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
                String foodItem = RestClient.findFoodName(params[0]);
                String foodObje = "";
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(foodItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            foodObje = jsonObject.toString();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Food foodSelected = gson.fromJson(foodObje,Food.class);
            int consAll = Integer.parseInt(RestClient.countAllConsump());
            int consumptionId = consAll + 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date();
            String curTimeC = sdf.format(date1);
            AppUser user = new AppUser();
            //AppUser user =(AppUser) getActivity().getIntent().getSerializableExtra("user");
            user.setUserid(user.getUserid());
            user.setUserid(((AppUser) getActivity().getIntent().getSerializableExtra("user")).getUserid());
            Consumption consumptionNew = new Consumption(consumptionId,curTimeC,"1",foodSelected,user);
            String result = " ";
            if( !consumptionNew.equals(null)){
                RestClient.createConsumption(consumptionNew);
                result = "Add successful";
                return result;
            }
            else
            {
                result = "Null value detected";
                return result;

                }


        }
        @Override
        protected void onPostExecute (String result){
            Toast.makeText(getActivity().getApplicationContext(),result, Toast.LENGTH_SHORT).show();

//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//            Food foodSelected = gson.fromJson(result,Food.class);
//            int consAll = Integer.parseInt(RestClient.countAllConsump());
//            int consumptionId = consAll + 1;
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//            Date date1 = new Date();
//            String curTimeC = sdf.format(date1);
//            AppUser user = (AppUser) getActivity().getIntent().getSerializableExtra("user");
//            Consumption consumptionNew = new Consumption(consumptionId,curTimeC,"1",foodSelected,user);
//            if( !consumptionNew.equals(null)){
//                RestClient.createConsumption(consumptionNew);
//                Toast.makeText(getActivity().getApplicationContext(),"add successful", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(getActivity().getApplicationContext(),"null value detected", Toast.LENGTH_SHORT).show();
//            }
        }
    }


}

