package com.example.finalyearproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.googleMap);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.googleMap, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);


        Button Menu = view.findViewById(R.id.MenuBtn);
        Button Marker = view.findViewById(R.id.MarkerBtn);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                Bundle MenuSelect = new Bundle();
                MenuSelect.putBoolean("MenuSelect", true);
                getParentFragmentManager().setFragmentResult("Menu", MenuSelect);

            }
        });

        return view;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        CrimeData data = new CrimeData();
        for (int i = 1; i < 10; i++) {
            crimeMarker(googleMap, i);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.4551,-0.9787),13));

        //MapRoute route = new MapRoute();

        //ArrayList<Double> lineCoordinates = null;
        //try {
            //lineCoordinates = route.addRoute("MiltonKeynes", "ReadingUniversity");
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}

        //Polyline pl = googleMap.addPolyline(new PolylineOptions()
        //        .clickable(true)
        //        .add(
        //                new LatLng(51.4551, -0.9787),
        //                new LatLng(lineCoordinates.get(0), lineCoordinates.get(1))));
    }


    public void crimeMarker(@NonNull GoogleMap googleMap, int lineNum) {
        Scanner crimeDataIs = new Scanner(getResources().openRawResource(R.raw.thames_valley_street));
        String CDdata = "";

        CrimeData data = new CrimeData();

        crimeDataIs.nextLine();
        int count = 0;
        while (count != lineNum) {
            CDdata = crimeDataIs.nextLine();
            count++;
        }
        //CDdata = input.nextLine();
        double llong = CrimeData.returnCoordinates(CDdata, "Longitude");
        double latt = CrimeData.returnCoordinates(CDdata, "Latitude");
        String crime = CrimeData.returnCrime(CDdata);

        LatLng markerCoordinates = new LatLng(latt, llong);
        googleMap.addMarker(new MarkerOptions()
                .position(markerCoordinates)
                .title(crime));
    }




}

