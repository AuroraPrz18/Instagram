package com.codepath.aurora.instagram;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.aurora.instagram.databinding.ActivityPostPhotoBinding;

public class PostPhotoActivity extends AppCompatActivity {
    ActivityPostPhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the ActivityLoginBinding object
        binding = ActivityPostPhotoBinding.inflate(getLayoutInflater());
        // Set the layout to this class
        setContentView(binding.getRoot());
    }



    public void onClickCamera(View view) {
    }
}