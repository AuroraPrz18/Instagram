package com.codepath.aurora.instagram;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.aurora.instagram.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the ActivityLoginBinding object
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // Set the layout to this class
        setContentView(binding.getRoot());
    }

    // Method that is called when the user try to login clicking the btnLogin
    public void onClickLogin(View view) {
        // Get the information provided
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        // TODO: Navigate to the main activity if the user has signed in properly
    }
}