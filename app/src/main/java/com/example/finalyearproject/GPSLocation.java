package com.example.finalyearproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPSLocation extends MainActivity{

    private LatLng userLocation;
    double lat = 0;
    double lng = 0;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Context ctx;

    public GPSLocation(Context context) {
        ctx = context;
    }
    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            //showInContextUI(...);
        } else {
            // You can directly ask for the permission.
            //requestPermissions(ctx,
            //        new String[] { Manifest.permission.ACCESS_FINE_LOCATION } );
        }
    }

    public void getLastLocation(Context context) {


        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("coarse not permitted!!!!!");
            userLocation = new LatLng(lat,lng);
            return;
        }
        else if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("fine not permitted!!!!!");
            userLocation = new LatLng(lat,lng);
            return;
        }

        System.out.println(lat);
        System.out.println(lng);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                        else {
                            lat = 0;
                            lng = 0;
                        }
                    }
                });
        userLocation = new LatLng(lat,lng);
    }

    public LatLng returnLocation() {
        return userLocation;
    }
}
