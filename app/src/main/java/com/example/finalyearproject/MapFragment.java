package com.example.finalyearproject;

import static android.text.TextUtils.substring;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.PolyUtil;


import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private List<Marker> markerList = new ArrayList<>();
    private List<Polyline> polylineList = new ArrayList<>();
    private String origin;
    private String destination;
    private LatLng startLoc;
    private LatLng endLoc;

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
        Switch Marker = view.findViewById(R.id.MarkerSwitch);
        EditText searchOrigin = view.findViewById(R.id.Origin);
        EditText searchDestination = view.findViewById(R.id.Destination);
        Button routeRemove = view.findViewById(R.id.removeRouteBtn);

        if (polylineList != null) {
            routeRemove.setVisibility(View.VISIBLE);
        }
        else {
            routeRemove.setVisibility(View.INVISIBLE);
        }

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                Bundle MenuSelect = new Bundle();
                MenuSelect.putBoolean("MenuSelect", true);
                getParentFragmentManager().setFragmentResult("Menu", MenuSelect);

            }
        });

        Marker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(origin != null) {
                        crimeMarker(map, origin);
                    }

                } else if (!isChecked) {
                    for (int i = 0; i < markerList.size(); i++) {
                        (markerList.get(i)).remove();
                    }
                    markerList.clear();
                }


            }
        });

        routeRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (polylineList != null) {
                    for (int i = 0; i < polylineList.size(); i++) {
                        (polylineList.get(i)).remove();
                    }
                    polylineList.clear();
                }
            }
        });

        searchOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origin = searchOrigin.getText().toString();
                if(destination != null) {
                    addRoutetoMap(map, origin, destination);

                    Marker start = map.addMarker(new MarkerOptions()
                            .position(startLoc)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    Marker end = map.addMarker(new MarkerOptions()
                            .position(endLoc)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    markerList.add(start);
                    markerList.add(end);

                }

            }
        });

        searchDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination = searchDestination.getText().toString();
                if(origin != null) {
                    System.out.println(origin + " working " + destination);
                    addRoutetoMap(map, origin, destination);

                    Marker start = map.addMarker(new MarkerOptions()
                            .position(startLoc)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    Marker end = map.addMarker(new MarkerOptions()
                            .position(endLoc)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    markerList.add(start);
                    markerList.add(end);

                }
                System.out.println(origin + " " + destination);
            }
        });
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.8177,-0.8192),9));

    }

    private void addRoutetoMap(GoogleMap googleMap, String origin, String destination) {

        Double[] originCD = new Double[2];
        Double[] destinationCD = new Double[2];
        SearchLocation loc = new SearchLocation();
        try {
            originCD = (loc.getSearch(origin));
            destinationCD = (loc.getSearch(destination));
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        if(originCD != null && destinationCD != null) {
            startLoc = new LatLng(originCD[0], originCD[1]);
            System.out.println(startLoc.latitude + "," + startLoc.longitude);
            endLoc = new LatLng(destinationCD[0], destinationCD[1]);
            System.out.println(endLoc.latitude + "," + endLoc.longitude);
        }
        else {
            return;
        }

        MapRoute route = null;


        try {
            route = new MapRoute(originCD[0], originCD[1], destinationCD[0], destinationCD[1]);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }

        String[] routePoly;
        routePoly = route.returnPolyline();

        int subcount = 0;
        int simpcount = 0;

        for(int i = 0; i < routePoly.length; i++) {
            if(routePoly[i] != null) {
                List<LatLng> simplifiedPoly = PolyUtil.simplify(PolyUtil.decode(routePoly[i]),50);
                simpcount = simpcount + simplifiedPoly.size();
                for(int j = 0; j < simplifiedPoly.size()-1; j++) {

                    Polyline pl = googleMap.addPolyline(new PolylineOptions()
                            .add(
                                    new LatLng((simplifiedPoly.get(j)).latitude, (simplifiedPoly.get(j)).longitude),
                                    new LatLng((simplifiedPoly.get(j + 1)).latitude, (simplifiedPoly.get(j + 1)).longitude)));
                    polylineList.add(pl);

                }
            }
        }
        System.out.println(startLoc);
        System.out.println(endLoc);
        System.out.println(subcount);
        System.out.println(simpcount);

    }


    public void crimeMarker(@NonNull GoogleMap googleMap, String city) {
        Scanner crimeDataIs = new Scanner(getResources().openRawResource(R.raw.thames_valley_street));
        String CDdata = "";
        ArrayList<String> crimeType = new ArrayList<String>();
        ArrayList<LatLng> crimeCD = new ArrayList<LatLng>();

        CrimeData data = new CrimeData();

        crimeDataIs.nextLine();
        int count = 0;
        boolean exitData = false;
        while (!exitData) {
            CDdata = crimeDataIs.nextLine();
            if((!Objects.equals(CrimeData.returnCity(CDdata), city)) && (count != 0)) {
                exitData = true;
            }
            else if(Objects.equals(CrimeData.returnCity(CDdata), city)) {
                crimeCD.add(count, new LatLng(CrimeData.returnCoordinates(CDdata, "Latitude"), CrimeData.returnCoordinates(CDdata, "Longitude")));
                crimeType.add(count, CrimeData.returnCrime(CDdata));
                count++;
                System.out.println(CrimeData.returnCoordinates(CDdata, "Latitude") + " " + CrimeData.returnCoordinates(CDdata, "Longitude") + " " + count);
            }

        }
        //CDdata = input.nextLine();
        for(int i = 0; i < crimeCD.size(); i++) {
            System.out.println(crimeCD.get(i).latitude + " " + crimeCD.get(i).longitude + "," + startLoc.latitude + " " + startLoc.longitude);
            if(crimeCD.get(i).latitude < startLoc.latitude + 0.01 && crimeCD.get(i).latitude > startLoc.latitude - 0.01) {
                if(crimeCD.get(i).longitude < startLoc.longitude + 0.01 && crimeCD.get(i).longitude > startLoc.longitude - 0.01) {
                    LatLng markerCoordinates = new LatLng(crimeCD.get(i).latitude, crimeCD.get(i).longitude);
                    Marker mkr = googleMap.addMarker(new MarkerOptions()
                            .position(markerCoordinates)
                            .title(crimeType.get(i)));
                    markerList.add(mkr);
                }
            }
        }

    }
}

