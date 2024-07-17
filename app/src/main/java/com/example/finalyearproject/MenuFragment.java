package com.example.finalyearproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.io.IOException;

public class MenuFragment extends Fragment {
    public MenuFragment() {
        super(R.layout.menu_fragment);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_fragment, container, false);

        // Initialize views

        Button MapBtn = view.findViewById(R.id.MapBtn);
        Button LogoutBtn = view.findViewById(R.id.LogoutBtn);
        TextView rte = view.findViewById(R.id.rte);

        // Set up button click listener
        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapRoute route = new MapRoute();
                double[][] routeCD;
                try {
                    routeCD = route.getRouteData("ReadingUniversity", "MiltonKeynes");
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
                String cd = null;
                //for (int i = 0; i < routeCD.length; i++) {
                //    cd = cd + routeCD[i][0] + " , ";
                //    cd = cd + routeCD[i][1] + "\n";
                //}

                cd  = cd + routeCD[0][0] + " , ";
                cd  = cd + routeCD[0][1] + " , \n";
                cd  = cd + routeCD[1][0] + " , ";
                cd  = cd + routeCD[1][1] + " , \n";
                cd  = cd + routeCD[2][0] + " , ";
                cd  = cd + routeCD[2][1] + " , \n";
                cd  = cd + routeCD[3][0] + " , ";
                cd  = cd + routeCD[3][1] + " , \n";
                cd  = cd + routeCD[4][0] + " , ";
                cd  = cd + routeCD[4][1] + " , \n";
                cd  = cd + routeCD[5][0] + " , ";
                cd  = cd + routeCD[5][1] + " , \n";
                    rte.setText(cd);

                // Handle button click
                //Bundle ReturnToMap = new Bundle();
                //ReturnToMap.putBoolean("Return", true);
                //getParentFragmentManager().setFragmentResult("MapReturn", ReturnToMap);
            }
        });

        return view;
    }
}
