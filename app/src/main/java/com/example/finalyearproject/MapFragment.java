package com.example.finalyearproject;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.Map;

public class MapFragment extends Fragment {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    LocationManager locationManager;
    private IMapController mapController;

    public MapFragment() {
        super(R.layout.map_fragment);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        // Use the fragment's context
        Context ctx = getContext(); // Or getContext()

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx)) ;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        map = (MapView) view.findViewById(R.id.map);
        startMap(map);

        //MapRoute mapRoute = new MapRoute(map, ctx);
        //mapRoute.addRoute(51.4489, -0.9502);

        //updateMap(52.0406, -0.7594);
        addMarker(51.4551,-0.9787, "Test Marker");
        addMarker(51.4489, -0.9502, "Test2");

        return view;
    }

    public void startMap(MapView map) {

        //create new map and map controller using OSM and set the zoom


        map.setTileSource(TileSourceFactory.MAPNIK);
        mapController = map.getController();
        mapController.setZoom(15);

        //create new geo point and set the center where the map initially loads into

        GeoPoint startPoint = new GeoPoint(51.4551,-0.9787); //GPS Tracker
        mapController.setCenter(startPoint);
    }

    public void DisplayRoute(double longitude, double latitude) {

    }

    public void addMarker(double latitude, double longitude, String markerName) {

        //Create new marker using osmdroid Marker method

        Marker startMarker = new Marker(map);


        //set GeoPoint location for marker

        GeoPoint startPoint = new GeoPoint(latitude,longitude);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        //use marker bitmap icon and set name of marker

        startMarker.setIcon(getResources().getDrawable(R.drawable.marker_icon));
        startMarker.setTitle(markerName);

        //add marker to map

        map.getOverlays().add(startMarker);

        map.invalidate();
    }


}
