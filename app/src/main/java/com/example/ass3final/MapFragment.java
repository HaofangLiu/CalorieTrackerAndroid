package com.example.ass3final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment {

    View vMain;

    private MapboxMap mMapboxMap;
    private MapView mMapView;
    double lat;
    double lng;
    private LatLng SAN_FRAN;// = new LatLng(lat, -lng);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vMain = inflater.inflate(R.layout.fragment_map, container, false);

        AppUser user =(AppUser) getActivity().getIntent().getSerializableExtra("user");
        Geocoder geocoder = new Geocoder(getActivity());
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<Address> addresses = geocoder.getFromLocationName(user.getAddress(), 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    stringBuilder.append(address.getAddressLine(i)).append("\n");
                }
                stringBuilder.append(address.getLatitude()).append("_");//
                stringBuilder.append(address.getLongitude());//
        }
        }
        catch (IOException e) {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        String laAndLo = stringBuilder.toString();
        String[] laAndL = laAndLo.split("_");
        ArrayList<Double> lDouble = new ArrayList<>();
        for(String a:laAndL){
            lDouble.add(Double.parseDouble(a));
        }
        lat = lDouble.get(0);
        lng = lDouble.get(1);
        SAN_FRAN = new LatLng(lat,lng);


        mMapView = (MapView) vMain.findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapView.setStreetMode();
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SAN_FRAN, 11));
                addMarker(mapboxMap);
                //addParkMarker(mapboxMap);
                AddMarker addMarker = new AddMarker();
                addMarker.execute(mapboxMap);
            }
        });
        return vMain;
    }

    private class AddMarker extends AsyncTask<MapboxMap, Void, String> {

        @Override
        protected String doInBackground(MapboxMap... params) {
            //MarkerOptions markerOptions = new MarkerOptions();
        String getPark = SearchParkAPI.searchAllPark(lat,lng);
        Log.d("result","show:::" + lat + ",,,," + lng);
        Log.d("result","show^^^" + getPark);
//        ArrayList<String> allPark = new ArrayList<>();
//        allPark = SearchParkAPI.findPark(getPark);
//        for(int i = 0; i <allPark.size() ;i +=3){
//            LatLng position = new LatLng(Double.parseDouble(allPark.get(i + 1)),Double.parseDouble(allPark.get(i + 2)));
//            markerOptions.position(position);
//            markerOptions.title("nearby parks");
//            markerOptions.snippet(allPark.get(i));
//            mMapboxMap.addMarker(markerOptions);
//
//        }
            return getPark;
    }

        @Override
        protected void onPostExecute(String getPark) {
            MarkerOptions markerOptions = new MarkerOptions();
            ArrayList<String> allPark = new ArrayList<>();
            allPark = SearchParkAPI.findPark(getPark);
            for(int i = 0; i <allPark.size() ;i +=3){
                LatLng position = new LatLng(Double.parseDouble(allPark.get(i + 1)),Double.parseDouble(allPark.get(i + 2)));
                markerOptions.position(position);
                markerOptions.title("nearby parks");
                markerOptions.snippet(allPark.get(i));
                mMapboxMap.addMarker(markerOptions);

            }
        }
    }




    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SAN_FRAN);
        markerOptions.title("MapQuest");
        markerOptions.snippet("Your position");
        mapboxMap.addMarker(markerOptions);
    }
    @Override
    public void onResume()
    { super.onResume();
    if(mMapView!=null)
    mMapView.onResume(); }
    @Override
    public void onPause()
    { super.onPause();     if(mMapView!=null)
        mMapView.onPause(); }
    @Override
    public void onDestroyView()
    { super.onDestroyView();     if(mMapView!=null)
        mMapView.onDestroy(); }
    @Override
    public void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState);
        if(mMapView!=null)
            mMapView.onSaveInstanceState(outState);
    }

}

