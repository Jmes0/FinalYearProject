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
                try {
                    rte.setText(route.addRoute("ReadingUniversity", "MiltonKeynes"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Handle button click
                //Bundle ReturnToMap = new Bundle();
                //ReturnToMap.putBoolean("Return", true);
                //getParentFragmentManager().setFragmentResult("MapReturn", ReturnToMap);
            }
        });

        return view;
    }
}
