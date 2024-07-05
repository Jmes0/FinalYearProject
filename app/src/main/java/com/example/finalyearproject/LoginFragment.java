package com.example.finalyearproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    public static boolean loginAccepted;

    public LoginFragment() {
        super(R.layout.login_fragment);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        // Initialize views

        Button button = view.findViewById(R.id.LoginBtn);

        // Set up button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                System.out.println("Hello");
                if(checkLogin()) {
                    loginAccepted = true;
                    Bundle loginResult = new Bundle();
                    loginResult.putBoolean("loginResult",loginAccepted);
                    getParentFragmentManager().setFragmentResult("requestKey", loginResult);
                }
            }
        });
        return view;
    }

    public boolean checkLogin() {
        return true;
    }


}
