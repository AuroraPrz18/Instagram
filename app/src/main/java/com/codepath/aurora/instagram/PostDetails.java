package com.codepath.aurora.instagram;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.codepath.aurora.instagram.adapters.CommentAdapter;
import com.codepath.aurora.instagram.databinding.ActivityPostDetailsBinding;
import com.codepath.aurora.instagram.models.Comment;
import com.codepath.aurora.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostDetails extends AppCompatActivity {
    Post post;
    List<Comment> comments;
    CommentAdapter adapter;
    ActivityPostDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("Post"));
        // Set the user name and description to the item post
        binding.tvUserName.setText(post.getUser().getUsername());
        String description = "<b>" + post.getUser().getUsername() + "</b> " +post.getDescription();
        binding.tvDescription.setText(Html.fromHtml(description));
        binding.tvTimestamp.setText(Post.getTimestamp(post.getCreatedAt()));
        // Set the image of the Post if it is not null
        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(this).load(image.getUrl()).into(binding.ivPost);
        }else{
            Glide.with(this).load("");
        }
        // Initialize the list of comments of this post
        comments = new ArrayList<>();
        // Set up the RecyclerView
        adapter = new CommentAdapter(this, comments);
        binding.rvComments.setLayoutManager(new LinearLayoutManager(this));
        binding.rvComments.setAdapter(adapter);

        queryComments();
    }

    /***
     * Method to get comments of an specific Post back from the database
     */
    public void queryComments(){
        // Specify that we are going to query the User class
        ParseQuery<Comment> query = ParseQuery.getQuery("Comment");
        // To include the author of the comment and the post
        query.include(Comment.KEY_USER);
        query.include(Comment.KEY_POST);
        // Set limit to the last 20 posts
        query.setLimit(20);
        // Set order them by creation date (desc)
        query.addAscendingOrder("createdAt");
        // Finds objects whose idPost = this post
        query.whereEqualTo(Comment.KEY_POST, post);
        // Retrieve all the items from the back end
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if( e != null){
                    Toast.makeText(PostDetails.this, "Something went wrong with getting comments", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Save comments
                comments.addAll(objects);
                // Notify the adapter
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Method called when there is a intention to add a comment to a post. It will save the comment
     * in our DB and update the RecyclerView
     * @param view
     */
    public void onClickPostComment(View view) {
        Comment comment = new Comment();
        // Populate de object
        String description = binding.etComment.getText().toString();
        if(description.isEmpty()){
            Toast.makeText(this, "The comment cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        comment.setUser(ParseUser.getCurrentUser());
        comment.setDescription(description);
        comment.setPost(post);

        // Saves the new object in our DB
        comment.saveInBackground(e -> {
            if (e==null){
                binding.etComment.setText("");
                queryComments();
            }else{
                //Something went wrong
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}