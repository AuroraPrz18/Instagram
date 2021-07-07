package com.codepath.aurora.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.aurora.instagram.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the ActivityLoginBinding object
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // Set the layout to this class
        setContentView(binding.getRoot());
        // Check if the user is already login
        if (ParseUser.getCurrentUser() != null){
            goMainActivity();
        }
    }

    // Method that is called when the user try to login clicking the btnLogin
    public void onClickLogin(View view) {
        // Get the information provided
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        // Navigate to the main activity if the user has signed in properly, the next method is going to be executed in the background thread
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // If the request fail, 'e' is not going to be null
                if( e != null ) {
                    Log.e("LOGIN" , "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Invalid credentials, try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(LoginActivity.this, "Success login!", Toast.LENGTH_SHORT).show();
                goMainActivity();
            }
        });
    }

    /**
     * Method to navigate to the Main Activity
     */
    private void goMainActivity() {
        Intent intent =  new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // To literally finish with this Activity
    }
}