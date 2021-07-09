package com.codepath.aurora.instagram;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.aurora.instagram.databinding.ActivityPostDetailsBinding;
import com.codepath.aurora.instagram.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetails extends AppCompatActivity {
    Post post;
    ActivityPostDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("Post"));
        // Set the user name and description to the item post
        binding.tvUserName.setText(post.getUser().getUsername());
        binding.tvDescription.setText(post.getDescription());
        binding.tvTimestamp.setText(Post.getTimestamp(post.getCreatedAt()));
        // Set the image of the Post if it is not null
        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(this).load(image.getUrl()).into(binding.ivPost);
        }else{
            Glide.with(this).load("");
        }
    }
}