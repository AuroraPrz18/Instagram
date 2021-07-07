package com.codepath.aurora.instagram;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.aurora.instagram.databinding.ActivitySignUpBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the ActivityLoginBinding object
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        // Set the layout to this class
        setContentView(binding.getRoot());
    }

    /** Method that is called when the user try to sign up clicking the btnSignUp **/
    public void onClickSignUp(View view) {
        // Get the information provided
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Try to sign it up in the background
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                // If the request fail, 'e' is not going to be null
                if (e != null) {
                    Log.e("SIGNUP" , "Issue with signup", e);
                    Toast.makeText(SignUpActivity.this, "Something goes wrong, try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(SignUpActivity.this, "Success sign up!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}