package com.example.finalyearproject;

import android.os.StrictMode;

import java.net.URI;
import java.net.URISyntaxException;

public class SearchLocation {
    public void getSearch(String origin, String destination) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URI routeURI = null;
        try {
            routeURI = new URI("https://maps.googleapis.com/maps/api/place/findplacefromtext/");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
