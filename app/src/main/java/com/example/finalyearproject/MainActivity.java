package com.example.finalyearproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        //inital loginfragment loaded

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, LoginFragment.class, null)
                    .commit();
        }


        getSupportFragmentManager().setFragmentResultListener("loginDetails", this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        boolean loginResult = result.getBoolean("loginResult");
                        String Username = result.getString("Username");
                        if(loginResult) {
                            replaceFragment(new MapFragment());
                        }
                    }
                });

        getSupportFragmentManager().setFragmentResultListener("Menu", this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        boolean MenuSelect = result.getBoolean("MenuSelect");
                        if(MenuSelect) {
                            addFragment(new MenuFragment());
                        }
                    }
                });

        getSupportFragmentManager().setFragmentResultListener("MapReturn", this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        boolean MenuSelect = result.getBoolean("MenuSelect");
                        if(MenuSelect) {
                            showFragment(new MenuFragment(), getSupportFragmentManager().findFragmentByTag("MapFragment"));
                        }
                    }
                });
    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //This could be abbreviated at a later date into one method
    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showFragment(Fragment fragment1, Fragment fragment2) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_view, fragment1);
        transaction.addToBackStack(null);
        transaction.hide(fragment2);
        transaction.commit();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}