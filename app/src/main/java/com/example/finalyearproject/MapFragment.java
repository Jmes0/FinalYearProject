package com.example.finalyearproject;

import static android.text.TextUtils.substring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;


import org.json.JSONException;

import java.io.IOException;
import java.util.List;
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

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.8177,-0.8192),9));
        MapRoute route = null;

        try {
            route = new MapRoute("ReadingUniversity", "MiltonKeynes");
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }

        String[] routePoly;
        routePoly = route.returnPolyline();

        int subcount = 0;
        int simpcount = 0;

        for(int i = 0; i < 25; i++) {
            List<LatLng> simplifiedPoly = PolyUtil.simplify(PolyUtil.decode(routePoly[i]),50);
            simpcount = simpcount + simplifiedPoly.size();
            for(int j = 0; j < simplifiedPoly.size()-1; j++) {
                if (i == 0 && j == 0) {
                    LatLng markerCoordinates = new LatLng(simplifiedPoly.get(j).latitude, simplifiedPoly.get(j).longitude);
                    googleMap.addMarker(new MarkerOptions()
                            .position(markerCoordinates));
                }
                else if(i == 24 && j == simplifiedPoly.size()-2) {
                    LatLng markerCoordinates = new LatLng(simplifiedPoly.get(j+1).latitude, simplifiedPoly.get(j+1).longitude);
                    googleMap.addMarker(new MarkerOptions()
                            .position(markerCoordinates));
                }

                Polyline pl = googleMap.addPolyline(new PolylineOptions()
                        .add(
                                new LatLng((simplifiedPoly.get(j)).latitude, (simplifiedPoly.get(j)).longitude),
                                new LatLng((simplifiedPoly.get(j + 1)).latitude, (simplifiedPoly.get(j + 1)).longitude)));
            }
        }
        System.out.println(subcount);
        System.out.println(simpcount);
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

