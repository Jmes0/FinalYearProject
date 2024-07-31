package com.example.finalyearproject;

import android.os.StrictMode;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SearchLocation {
    public Double[] getSearch(String location) throws JSONException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String[] locationStr = location.split(" ");
        URI routeURI = null;

        String HttpRequest = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?fields=formatted_address%2Cname%2Cgeometry&input=Google%20";

        if(locationStr.length == 1) {
            HttpRequest = HttpRequest + locationStr[0];
        }
        else {
            for(int i = 0; i < locationStr.length; i++) {
                if(i != locationStr.length-1) {
                    HttpRequest = HttpRequest + locationStr[i] + "%20";
                }
                else{
                    HttpRequest = HttpRequest + locationStr[i];
                }
            }
        }

        HttpRequest = HttpRequest + "%20UK&inputtype=textquery&key=AIzaSyBYPZuO-vzvE0By_-6iyI9dl3ZNXKPUrGo";

        try {
            routeURI = new URI(HttpRequest);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        URL routeURL = null;
        try {
            routeURL = routeURI.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
        //System.out.println(routeStr);


        JSONObject jsonObject = new JSONObject(routeStr);
        JSONArray routes = jsonObject.getJSONArray("candidates");
        Double[] locationCD = new Double[2];


        for (int i = 0; i < routes.length(); i++) {
            JSONObject route = routes.getJSONObject(i);
            JSONObject legs = route.getJSONObject("geometry");

            JSONObject leg = legs.getJSONObject("location");

            locationCD[0] = leg.getDouble("lat");
            locationCD[1] = leg.getDouble("lng");

            if(i == 0) {
                System.out.println(locationCD[0] + " " + locationCD[1]);
            }

        }

        return locationCD;
    }
}
