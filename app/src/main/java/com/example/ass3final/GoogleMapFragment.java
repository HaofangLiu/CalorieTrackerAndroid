package com.example.ass3final;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GoogleMapFragment extends Fragment {

    View mapView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mapView = inflater.inflate(R.layout.fragment_google_map, container, false);
        return null;
    }
}
