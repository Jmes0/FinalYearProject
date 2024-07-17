package com.example.finalyearproject;

import android.os.StrictMode;
import android.util.JsonToken;

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
import java.sql.Array;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapRoute extends MainActivity{

    public double[][] getRouteData(String origin, String destination) throws IOException, JSONException {

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
        JSONArray routes = jsonObject.getJSONArray("routes");
        String routeCoordinates = null;
        double[][] latLngArray = new double[200][200];

        for (int i = 0; i < routes.length(); i++) {
            JSONObject route = routes.getJSONObject(i);
            JSONArray legs = route.getJSONArray("legs");

            for (int j = 0; j < legs.length(); j++) {
                JSONObject leg = legs.getJSONObject(j);
                JSONArray steps = leg.getJSONArray("steps");

                for (int k = 0; k < steps.length(); k++) {
                    JSONObject step = steps.getJSONObject(k);
                    JSONObject startCD = step.getJSONObject("start_location");
                    JSONObject endCD = step.getJSONObject("end_location");
                    //routeCoordinates = startCD.toString();

                    latLngArray[j*2][0] = startCD.getDouble("lat");
                    latLngArray[j*2][1] = startCD.getDouble("lng");
                    latLngArray[(j*2)+1][0] = endCD.getDouble("lat");
                    latLngArray[(j*2)+1][1] = endCD.getDouble("lng");

                    //routeCoordinates = routeCoordinates + step.getString("start_location") + "," + step.getString("end_location");
                    ;
                    // Process the instructions or other step data


                    //ArrayList<Double> routeCoordinates = new ArrayList<Double>();
                    //routeCoordinates.add(51.4403);
                    //routeCoordinates.add(-0.9421);
                    //return routeCoordinates;
                }
            }
        }
        return latLngArray;
    }


}
