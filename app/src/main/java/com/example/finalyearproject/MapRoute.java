package com.example.finalyearproject;

import android.os.StrictMode;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class MapRoute extends MainActivity{

    public String addRoute(String origin, String destination) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URI routeURI = null;
        try {
            routeURI = new URI("https://maps.googleapis.com/maps/api/directions/json?origin=" +
                    origin + "&destination=" +
                    destination + "&key=AIzaSyBYPZuO-vzvE0By_-6iyI9dl3ZNXKPUrGo");
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        URL routeURL = null;
        try {
            routeURL = routeURI.toURL();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(routeURL.toString());

        HttpURLConnection conn = (HttpURLConnection) routeURL.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
            content.append(System.getProperty("line.separator"));
        }

        in.close();
        conn.disconnect();

        String routeStr = content.toString();
        System.out.println(routeStr);
        return routeStr;

        //ArrayList<Double> routeCoordinates = new ArrayList<Double>();
        //routeCoordinates.add(51.4403);
        //routeCoordinates.add(-0.9421);
        //return routeCoordinates;
    }

}
